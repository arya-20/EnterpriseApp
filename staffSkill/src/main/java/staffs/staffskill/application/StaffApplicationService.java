package staffs.staffskill.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import example.common.domain.Identity;
import example.common.domain.UniqueIDFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.api.CreateStaffCommand;
import staffs.staffskill.domain.Staff;
import staffs.staffskill.domain.StaffSkill;
import staffs.staffskill.infrastructure.StaffRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StaffApplicationService {
    private StaffRepository staffRepository;
    private Environment env;
    private RabbitTemplate sender;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public String createStaffWithSkills(CreateStaffCommand command) throws StaffDomainException {
        try {
            Identity idOfNewStaff = UniqueIDFactory.createID();
            // Convert staff skills from the command as we need to pass them to the aggregate constructor
            List<StaffSkill> staffSkills = command.getStaffSkills().stream()
                    .map(staffSkill -> new StaffSkill(
                            -1,
                            staffSkill.skillName(),
                            staffSkill.expiryDate(),
                            staffSkill.levelOfSkill(),
                            staffSkill.staffId(),
                            staffSkill.notes()
                    ))
                    .collect(Collectors.toList());
            // Create aggregate to confirm valid state of values and creating an event
            Staff staff = Staff.staffOf(idOfNewStaff, command.getFullName(), command.getManagerId(), command.getRole(), staffSkills);
            // Save the staff entity to the repository and retrieve it
            BaseStaff staffEntity = staffRepository.save(StaffDomainToInfrastructureConverter.convert(staff));
            // Convert the saved entity back to domain to retrieve any generated events
            staff = StaffInfrastructureToDomainConverter.convert(staffEntity);
            LOG.info("Checking staff skills after creation: " + staff);
            publishNewStaffEvent(staff); // Notify subscribers of the new staff

            // Return the new staff ID to the controller
            return staff.id().toString();
        } catch (IllegalArgumentException e) {
            throw new StaffDomainException(e.getMessage());
        }
    }

    private void publishNewStaffEvent(Staff staff) throws StaffDomainException {
        if (staff.getEvent().isEmpty()) {
            throw new StaffDomainException("New Staff Event not generated by domain");
        }
        try {
            // Send the event using the exchange and routing key from RabbitMQ configuration
            sender.convertAndSend(
                    Objects.requireNonNull(env.getProperty("rabbitmq.exchange")),
                    Objects.requireNonNull(env.getProperty("rabbitmq.newStaffKey")),
                    staff.getEvent().get().toJson()
            );

            // Consider storing the event in an event store here if needed
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        }
    }
}

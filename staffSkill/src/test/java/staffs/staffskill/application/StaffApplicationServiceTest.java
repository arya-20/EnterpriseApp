package staffs.staffskill.application;

import example.common.domain.Identity;
import example.common.domain.UniqueIDFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.api.CreateStaffCommand;
import staffs.staffskill.api.events.StaffCreatedEvent;
import staffs.staffskill.domain.Staff;
import staffs.staffskill.domain.StaffSkill;
import staffs.staffskill.infrastructure.StaffRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StaffApplicationServiceTest {

    private StaffRepository staffRepository;
    private Environment env;
    private RabbitTemplate sender;
    private StaffApplicationService staffApplicationService;

    @BeforeEach
    void setUp() {
        staffRepository = mock(StaffRepository.class);
        env = mock(Environment.class);
        sender = mock(RabbitTemplate.class);
        staffApplicationService = new StaffApplicationService(staffRepository, env, sender);
    }

    @Test
    @DisplayName("Should create a new staff with skills and return the staff ID")
    void testCreateStaffWithSkills() throws Exception {
        CreateStaffCommand command = mock(CreateStaffCommand.class);
        when(command.getFullName()).thenReturn("Test test");
        when(command.getManagerId()).thenReturn("managerId");
        when(command.getRole()).thenReturn("developer");
        when(command.getStaffSkills()).thenReturn(Collections.emptyList());

        Identity generatedId = UniqueIDFactory.createID();
        BaseStaff savedStaffEntity = mock(BaseStaff.class);
        when(savedStaffEntity.getId()).thenReturn(String.valueOf(generatedId));

        when(staffRepository.save(any(BaseStaff.class))).thenReturn(savedStaffEntity);

        String staffId = staffApplicationService.createStaffWithSkills(command);

        assertEquals(generatedId.toString(), staffId);
        verify(staffRepository, times(1)).save(any(BaseStaff.class));
    }

    @Test
    @DisplayName("Should update an existing staff with new details")
    void testUpdateStaffWithDetails() throws Exception {
        CreateStaffCommand command = mock(CreateStaffCommand.class);
        when(command.getFullName()).thenReturn("test test");
        when(command.getManagerId()).thenReturn("managerId");
        when(command.getRole()).thenReturn("Manager");
        when(command.getStaffSkills()).thenReturn(Collections.emptyList());

        String staffId = "staffId";
        BaseStaff existingStaffEntity = mock(BaseStaff.class);

//        when(staffRepository.findById(staffId)).thenReturn(Optional.of(existingStaffEntity));
        when(staffRepository.save(any(BaseStaff.class))).thenReturn(existingStaffEntity);

        String updatedStaffId = staffApplicationService.updateStaffWithDetails(staffId, command);

        assertEquals(staffId, updatedStaffId);
        verify(staffRepository, times(1)).findById(staffId);
        verify(staffRepository, times(1)).save(any(BaseStaff.class));
    }

    @Test
    @DisplayName("Should throw StaffDomainException if staff not found during update")
    void testUpdateStaffWithDetails_StaffNotFound() {
        CreateStaffCommand command = mock(CreateStaffCommand.class);
        when(staffRepository.findById("staffId")).thenReturn(Optional.empty());

        assertThrows(StaffDomainException.class, () -> {
            staffApplicationService.updateStaffWithDetails("staffId", command);
        });
    }

    @Test
    @DisplayName("Should remove staff by ID if present")
    void testRemoveStaff() throws StaffDomainException {
        String staffId = "staffId";
        staffs.staffskill.infrastructure.Staff existingStaff = mock(staffs.staffskill.infrastructure.Staff.class);

        when(staffRepository.findById(staffId)).thenReturn(Optional.of(existingStaff));
        staffApplicationService.removeStaff(staffId);
        verify(staffRepository, times(1)).deleteById(staffId);
    }

    @Test
    @DisplayName("Should throw StaffDomainException if staff not found during removal")
    void testRemoveStaff_StaffNotFound() {
        String staffId = "staffId";

        when(staffRepository.findById(staffId)).thenReturn(Optional.empty());
        assertThrows(StaffDomainException.class, () -> {
            staffApplicationService.removeStaff(staffId);
        });
    }

    @Test
    @DisplayName("Should publish new staff event after creation")
    void testPublishNewStaffEvent() throws Exception {
        Staff staff = mock(Staff.class);
        when(staff.getEvent()).thenReturn(Optional.of(mock(StaffCreatedEvent.class)));
        when(env.getProperty("rabbitmq.exchange")).thenReturn("exchange");
        when(env.getProperty("rabbitmq.newStaffKey")).thenReturn("newStaffKey");

        staffApplicationService.publishNewStaffEvent(staff);

        verify(sender, times(1)).convertAndSend(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Should throw StaffDomainException if new staff event not generated")
    void testPublishNewStaffEvent_NoEventGenerated() {
        Staff staff = mock(Staff.class);
        when(staff.getEvent()).thenReturn(Optional.empty());

        assertThrows(StaffDomainException.class, () -> {
            staffApplicationService.publishNewStaffEvent(staff);
        });
    }
}

package staffs.staffskill.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.infrastructure.StaffRepository;

import java.util.Optional;


@Service
@AllArgsConstructor
public class StaffQueryHandler {
    private StaffRepository staffRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public Optional<GetStaffResponse> getStaff(String staffId) {
        return staffRepository.findById(staffId).map(staff ->
                modelMapper.map(staff, GetstaffResponse.class));
    }

    public Iterable<BaseStaff> getAllStaff() {
        return staffRepository.findAllStaff();
    }

    public Optional<GetStaffStaffSkillResponse> getStaffStaffskill(String staffId) {
        return staffRepository.findById(staffId).map(staff ->
                modelMapper.map(staff, GetStaffStaffSkillResponse.class));
    }
}

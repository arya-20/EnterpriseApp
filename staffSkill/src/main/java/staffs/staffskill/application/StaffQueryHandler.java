package staffs.staffskill.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import staffs.staffskill.api.*;
import staffs.staffskill.infrastructure.StaffRepository;
import org.modelmapper.ModelMapper;
import staffs.staffskill.infrastructure.StaffSkillRepository;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class StaffQueryHandler {
    private final StaffSkillRepository staffSkillRepository;
    private StaffRepository staffRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public Optional<GetStaffResponse> getStaff(String staffId) {
        return staffRepository.findById(staffId).map(staff ->
                modelMapper.map(staff, GetStaffResponse.class));
    }

    public Iterable<BaseStaff> getAllStaff() {
        return staffRepository.findAllStaff();
    }

    public Optional<GetStaffSkillResponse> getStaffskill(String staffId) {
        return staffRepository.findById(staffId).map(staff ->
                modelMapper.map(staff, GetStaffSkillResponse.class));
    }

    public List<BaseStaffSkill> getStaffByStaffSkills(String id)  {
        return staffSkillRepository.findByStaffSkill(id);
    }

    public List<BaseStaff> getStaffByManagerId(String managerId) {
        return staffRepository.findByManagerId(managerId);
    }
}

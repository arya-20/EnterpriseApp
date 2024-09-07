package staffs.staffskill.application;

import example.common.domain.Identity;
import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.domain.Staff;
import staffs.staffskill.domain.StaffSkill;

import java.util.ArrayList;
import java.util.List;

public class StaffInfrastructureToDomainConverter {
    public static Staff convert(BaseStaff staff) {
        List<StaffSkill> staffSkills = new ArrayList<>();

        for (staffs.staffskill.infrastructure.StaffSkill staffSkillValueObject : staff.getStaffSkills()) {
            staffSkills.add(new StaffSkill(
                    staffSkillValueObject.getId(),
                    staffSkillValueObject.getName(),
                    staffSkillValueObject.getExpiry(),
                    staffSkillValueObject.getLevelOfSkill(),
                    staffSkillValueObject.getNotes(),
                    staffSkillValueObject.getStaffFullName()
            ));
        }

        // Map to domain
        return Staff.staffOf(
                new Identity(staff.getId()),
                staff.getFullName(),
                staff.getManagerId(),
                staff.getRole(),
                staffSkills
        );

    }
}

package staffs.staffskill.application;

import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.domain.Staff;
import staffs.staffskill.domain.StaffSkill;

public class StaffDomainToInfrastructureConverter {
    public static BaseStaff convert(Staff staff) {
        BaseStaff s = staffs.staffskill.infrastructure.Staff.staffOf(staff.id().toString(),
                staff.fullName().toString(),
                staff.managerId(),
                staff.role());

        for(StaffSkill staffSkillValueObject : staff.staffSkills()) {
            s.addStaffSkill(new staffs.staffskill.infrastructure.StaffSkill(staffSkillValueObject.id(),
                    staffSkillValueObject.skillName(),
                    staffSkillValueObject.expiryDate(),
                    staffSkillValueObject.levelOfSkill(),
                    staffSkillValueObject.notes(),
                    staffSkillValueObject.staffId(),
                    staffSkillValueObject.staffFullName()));
        }
        return s;
    }
}

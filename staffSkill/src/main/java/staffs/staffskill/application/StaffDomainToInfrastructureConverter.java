package staffs.staffskill.application;

import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.api.BaseStaffSkill;
import staffs.staffskill.domain.Staff;
import staffs.staffskill.infrastructure.StaffSkill;

public class StaffDomainToInfrastructureConverter {
    public static BaseStaff convert(Staff staff) {
        BaseStaff s = staffs.staffskill.infrastructure.Staff.staffOf(staff.id().toString(),
                staff.fullName(),
                staff.managerId(),
                staff.role());

        for(BaseStaffSkill staffSkillValueObject : staff.staffSkills()) {
            StaffSkill staffSkill = new StaffSkill(staffSkillValueObject.id(),
                    staffSkillValueObject.skillName(),
                    staffSkillValueObject.expiryDate(),
                    staffSkillValueObject.levelOfSkill(),
                    staffSkillValueObject.notes(),
                    staff.id().toString());
            s.addStaffSkill(staffSkill);
        }
        return s;
    }
}

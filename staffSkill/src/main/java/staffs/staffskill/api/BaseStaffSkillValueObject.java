package staffs.staffskill.api;

import java.util.Date;

public interface BaseStaffSkillValueObject {
    long getId();
    String getName();
    Date getExpiry();
    String getLevelOfSkill();
    String getNotes();
    String getStaffFullName();
    String getStaff_id();

}

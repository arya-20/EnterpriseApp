package staffs.staffskill.api;

import java.time.LocalDate;

public interface BaseStaffSkillValueObject {
    long getId();
    String getName();
    LocalDate getExpiry();
    String getLevelOfSkill();
    String getNotes();
    String getStaff_id();

}

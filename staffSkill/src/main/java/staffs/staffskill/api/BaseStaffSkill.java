package staffs.staffskill.api;

import java.util.Date;

public interface BaseStaffSkill {

    long id();

    String skillName();

    Date expiryDate();

    String levelOfSkill();

    String staffId();

    String notes();
}
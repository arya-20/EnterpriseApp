package staffs.staffskill.api;

import java.time.LocalDate;
import java.util.Date;

public interface BaseStaffSkill {

    long id();

    String skillName();

    LocalDate expiryDate();

    String levelOfSkill();

    String staffId();

    String notes();
}
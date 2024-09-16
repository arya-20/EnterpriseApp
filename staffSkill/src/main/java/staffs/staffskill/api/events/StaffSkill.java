package staffs.staffskill.api.events;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import example.common.domain.Money;
import lombok.AllArgsConstructor;
import lombok.Setter;
import staffs.staffskill.api.BaseStaffSkill;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Setter
@JsonSerialize(using = StaffSkillCustomSerializer.class)
//Version for events (based on domain MenuItems) so that reuse of this event class by another context
//is not coupled to the domain version
public class StaffSkill implements BaseStaffSkill { //Need the interface to decouple event version in Restaurant factory method
    private long id;
    private String skillName;
    private LocalDate expiry;
    private String levelOfSkill;
    private String staffId;
    private String notes;

    public long id(){return id;}

    public String skillName() {
        return skillName;
    }

    public LocalDate expiryDate() {
        return expiry;
    }

    public String levelOfSkill() {return levelOfSkill;}

    public String staffId() {return staffId;}

    public String notes() {return notes;}

    public String toString(){
        return String.format("id=%s, skill name=%s, expiry=%s, Level of Skill=%s, staff Id=%s, expiry=%s, notes=%s,",
                id(), skillName, expiry, levelOfSkill, staffId, expiry, notes);
    }
}
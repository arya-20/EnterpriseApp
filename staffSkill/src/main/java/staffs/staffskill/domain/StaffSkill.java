package staffs.staffskill.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import staffs.staffskill.api.BaseStaffSkill;
import staffs.staffskill.api.events.StaffSkillCustomSerializer;
import example.common.domain.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonSerialize(using = StaffSkillCustomSerializer.class)
public class StaffSkill extends ValueObject implements BaseStaffSkill {

    private long id;
    private String skillName;
    private Date expiryDate;
    private String levelOfSkill;
    private String staffId;
    private String notes;
    private String staffFullName;

    // Constructor
    public StaffSkill(long id, String skillName, Date expiryDate, String levelOfSkill, String staffId, String notes) {
        setId(id);
        setSkillName(skillName);
        setExpiryDate(expiryDate);
        setLevelOfSkill(levelOfSkill);
        setStaffId(staffId);
        setNotes(notes);
        setStaffFullName(staffFullName);
    }

    // Getters
    public long id() {
        return id;
    }

    public String skillName() {
        return skillName;
    }

    public Date expiryDate() {
        return expiryDate;
    }

    public String levelOfSkill() {
        return levelOfSkill;
    }

    public String notes() {
        return notes;
    }

    public String staffId() {
        return staffId;
    }

    public String staffFullName() {
        return staffFullName;
    }

    // Private Setters
    private void setId(long id) {
        assertArgumentNotEmpty(id, "ID cannot be empty");
        this.id = id;
    }

    private void setSkillName(String skillName) {
        assertArgumentNotEmpty(skillName, "Skill Name cannot be empty");
        this.skillName = skillName;
    }

    private void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate != null ? expiryDate : new Date();
    }

    private void setLevelOfSkill(String levelOfSkill) {
        assertArgumentNotEmpty(levelOfSkill, "Level of Skill cannot be empty");
        this.levelOfSkill = levelOfSkill;
    }

    private void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    private void setStaffId(String staffId) {
        assertArgumentNotEmpty(staffId, "Staff ID cannot be empty");
        this.staffId = staffId;
    }

    private void setStaffFullName(String staffFullName) {
        assertArgumentNotEmpty(staffFullName, "Staff Full Name cannot be empty");
        this.staffFullName = staffFullName;
    }

    public String toString() {
        return String.format("id=%s, skillName=%s, expiryDate=%s, levelOfSkill=%s, staffId=%s, notes=%s, staffFullName=%s",
                id, skillName, expiryDate, levelOfSkill, staffId, notes, staffFullName);
    }
}

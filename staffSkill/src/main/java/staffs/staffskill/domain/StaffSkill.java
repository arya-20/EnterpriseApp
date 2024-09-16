package staffs.staffskill.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import staffs.staffskill.api.BaseStaffSkill;
import staffs.staffskill.api.events.StaffSkillCustomSerializer;
import example.common.domain.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonSerialize(using = StaffSkillCustomSerializer.class)
public class StaffSkill extends ValueObject implements BaseStaffSkill {
    private long id;
    private String skillName;
    private LocalDate expiryDate;
    private String levelOfSkill;
    private String staffId;
    private String notes;

    // Constructor
    public StaffSkill(long id, String skillName, LocalDate expiryDate, String levelOfSkill, String staffId, String notes) {
        setId(id);
        setSkillName(skillName);
        setExpiryDate(expiryDate);
        setLevelOfSkill(levelOfSkill);
        setStaffId(staffId);
        setNotes(notes);
    }

    // Getters
    public long id() {
        return id;
    }

    public String skillName() {
        return skillName;
    }

    public LocalDate expiryDate() {
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


    // Private Setters
    private void setId(long id) {
        assertArgumentNotEmpty(id, "ID cannot be empty");
        this.id = id;
    }

    private void setSkillName(String skillName) {
        assertArgumentNotEmpty(skillName, "Skill Name cannot be empty");
        this.skillName = skillName;
    }

    private void setExpiryDate(LocalDate expiryDate) {

        this.expiryDate = expiryDate != null ? expiryDate : LocalDate.now();
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

    public String toString() {
        return String.format("id=%s, skillName=%s, expiryDate=%s, levelOfSkill=%s, staffId=%s, notes=%s",
                id, skillName, expiryDate, levelOfSkill, staffId, notes);
    }
}

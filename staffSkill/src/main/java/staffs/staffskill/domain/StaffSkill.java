package staffs.staffskill.domain;

import example.common.domain.Entity;
import example.common.domain.Identity;
import lombok.ToString;

@ToString
public class StaffSkill extends Entity {

    public static StaffSkill staffSkillOf(Identity id,
                                          String expiry,
                                          String skillId,
                                          String skillName,
                                          String levelOfSkill,
                                          String staffId,
                                          String notes,
                                          String staffFullName) {
        return new StaffSkill(id, expiry, skillId, skillName, levelOfSkill, staffId, notes, staffFullName);
    }

    private String expiry;
    private String skillId;
    private String skillName;
    private String levelOfSkill;
    private String staffId;
    private String notes;
    private String staffFullName;

    public StaffSkill(Identity id,
                      String expiry,
                      String skillId,
                      String skillName,
                      String levelOfSkill,
                      String staffId,
                      String notes,
                      String staffFullName) {
        super(id);
        setExpiry(expiry);
        setSkillId(skillId);
        setSkillName(skillName);
        setLevelOfSkill(levelOfSkill);
        setStaffId(staffId);
        setNotes(notes);
        setStaffFullName(staffFullName);
    }

    public String expiry() {return expiry;}
    private void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String skillId() {return skillId;}
    private void setSkillId(String skillId) {
        assertArgumentNotEmpty(skillId, "Skill ID cannot be empty");
        this.skillId = skillId;
    }

    public String skillName() {return skillName;}
    private void setSkillName(String skillName) {
        assertArgumentNotEmpty(skillName, "Skill name cannot be empty");
        this.skillName = skillName;
    }

    public String levelOfSkill() {return levelOfSkill;}
    private void setLevelOfSkill(String levelOfSkill) {
        assertArgumentNotEmpty(levelOfSkill, "Level of skill cannot be empty");
        this.levelOfSkill = levelOfSkill;
    }

    public String staffId() {return staffId;}
    private void setStaffId(String staffId) {
        assertArgumentNotEmpty(staffId, "Staff ID cannot be empty");
        this.staffId = staffId;
    }

    public String notes() {return notes;}
    private void setNotes(String notes) {
        this.notes = notes;
    }

    public String staffFullName() {return staffFullName;}
    private void setStaffFullName(String staffFullName) {
        assertArgumentNotEmpty(staffFullName, "Staff name cannot be empty");
        this.staffFullName = staffFullName;
    }
}

package staffs.staffskill.domain;

import example.common.domain.Entity;
import example.common.domain.Identity;
import staffs.staffskill.api.BaseStaffSkill;
import staffs.staffskill.api.events.StaffCreatedEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Staff extends Entity {
    private List<BaseStaffSkill> staffSkills;
    private String fullName;
    private String managerId;
    private String role;

    // Factory method
    public static Staff staffOf(Identity id, String fullName, String managerId, String role, List<BaseStaffSkill> staffSkills) {
        return new Staff(id, fullName, managerId, role, staffSkills);
    }

    public Staff(Identity id, String fullName, String managerId, String role, List<BaseStaffSkill> staffSkills) {
        super(id);
        setFullName(fullName);
        setManagerId(managerId);
        setRole(role);
        setStaffSkills(staffSkills);

        // Store event
        event = Optional.of(new StaffCreatedEvent(id.toString(), fullName, managerId, role, staffSkills));


    }

    public String fullName() {return fullName;}

    private void setStaffSkills (List<BaseStaffSkill> staffSkills) {
        assertArgumentNotEmpty(staffSkills, "staff skills can not be empty");
        this.staffSkills = staffSkills;
    }

    private void setFullName(String fullName) {
        assertArgumentNotEmpty(fullName, "Name cannot be empty");
        this.fullName = fullName;
    }

    public String managerId() {return managerId;}

    private void setManagerId(String managerId) {
        assertArgumentNotEmpty(managerId, "Manager ID cannot be empty");
        this.managerId = managerId;
    }

    public String role() {return role;}

    private void setRole(String role) {
        assertArgumentNotEmpty(role, "Role cannot be empty");
        this.role = role;
    }

    public List<BaseStaffSkill> staffSkills() {return staffSkills;}

    public boolean findStaffSkill(long staffSkillId) {
        return staffSkills.stream()
                .anyMatch(staffSkill -> staffSkill.id() == staffSkillId);
    }

    public String toString() {
        String staffSkillsAsString = staffSkills.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("\nStaff: %s, Name: %s, Manager ID: %s, Role: %s, Staff Skills: \n[%s]", id(), fullName, managerId, role, staffSkillsAsString);
    }

    public void updateDetails(String fullName, String managerId, String role, List<BaseStaffSkill> staffSkills) {
        this.fullName = fullName;
        this.managerId = managerId;
        this.role = role;
        this.staffSkills = staffSkills;
    }

}

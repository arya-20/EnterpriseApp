package staffs.staffskill.domain;

import example.common.domain.Entity;
import example.common.domain.FullName;
import example.common.domain.Identity;
import staffs.staffskill.application.events.StaffCreatedEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Staff extends Entity {
    private final List<StaffSkill> staffSkills;
    private FullName fullName;
    private String managerId;
    private String role;

    // Factory method
    public static Staff staffOf(Identity id, FullName fullName, String managerId, String role, List<StaffSkill> staffSkills) {
        return new Staff(id, fullName, managerId, role, staffSkills);
    }

    public Staff(Identity id, FullName fullName, String managerId, String role, List<StaffSkill> staffSkills) {
        super(id);
        setFullName(fullName);
        setManagerId(managerId);
        setRole(role);
        this.staffSkills = staffSkills;

        // Store event
        event = Optional.of(new StaffCreatedEvent(id.toString(), fullName.toString(), managerId, role, staffSkills));
    }

    public FullName fullName() {return fullName;}

    private void setFullName(FullName fullName) {
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

    public List<StaffSkill> staffSkills() {return staffSkills;}

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
}

package staffs.staffskill.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import staffs.staffskill.domain.StaffSkill;

import java.util.List;
import java.util.stream.Collectors;

@Getter // Needed for JSON
@AllArgsConstructor
public class CreateStaffCommand {
    private String fullName;
    private String managerId;
    private String role;
    private List<StaffSkill> staffSkills;

    public String toString() {
        String staffSkillsAsString = staffSkills.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("Staff name: %s, manager Id: %s, role: %s, skills: \n[%s]",
                fullName, managerId, role, staffSkillsAsString);
    }
}

package staffs.skill.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import staffs.skill.domain.SkillDetail;

import java.util.List;
import java.util.stream.Collectors;

@Getter // Needed for JSON serialization
@AllArgsConstructor
public class CreateSkillCommand {
    private String skillName;
    private String SkillCategory;
    private List<SkillDetail> skillDetails;

    public String toString() {
        String skillDetailAsString = skillDetails.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));


        return String.format("\nSkill name: %s, \nCategory: %s, \nSkill details: %s", skillName, SkillCategory, skillDetailAsString);
    }
}

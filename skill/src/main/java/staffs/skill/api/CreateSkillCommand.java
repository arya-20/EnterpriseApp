package staffs.skill.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import staffs.skill.domain.SkillDetail;

import java.util.List;

@Getter // Needed for JSON serialization
@AllArgsConstructor
public class CreateSkillCommand {
    private String skillName;
    private String category;
    private List<SkillDetail> skillDetail;

    @Override
    public String toString() {
        return String.format("Skill name: %s, Category: %s", skillName, category);
    }
}

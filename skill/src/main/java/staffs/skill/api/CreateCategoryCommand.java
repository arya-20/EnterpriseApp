package staffs.skill.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import staffs.skill.domain.SkillDetail;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateCategoryCommand {
    private String categoryId;
    private String categoryName;

    @Override
    public String toString() {
        return String.format("categoryId: %s, categoryName: %s", categoryId, categoryName);
    }
}

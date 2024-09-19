package staffs.skill.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import staffs.skill.infrastructure.Category;

@Getter
@Setter
@NoArgsConstructor
public class GetSkillResponse {
    private String id;
    private String name;
    private Category category;
}
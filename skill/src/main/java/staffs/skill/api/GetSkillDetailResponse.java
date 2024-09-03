package staffs.skill.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetSkillDetailResponse {
    private String skillId;
    private String name;
    private List<BaseSkillDetailValueObject> skillDetails;
}

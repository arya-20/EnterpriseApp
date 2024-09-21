package staffs.skill.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetSkillResponse {
    private String id;
    private String name;
    private String category;

}
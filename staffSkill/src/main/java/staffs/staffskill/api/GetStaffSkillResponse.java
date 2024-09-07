package staffs.staffskill.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter //Needed for JSON
@Setter
@NoArgsConstructor
public class GetStaffSkillResponse {
    private String staffId;
    private String fullName;
    private String managerId;
    private String role;
    private List<BaseStaffSkillValueObject> staffSkills;
}

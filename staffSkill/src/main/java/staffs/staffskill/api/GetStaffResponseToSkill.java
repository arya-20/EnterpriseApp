package staffs.staffskill.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter //Needed for JSON
@Setter
@NoArgsConstructor
public class GetStaffResponseToSkill {
    private String staffId;
    private String fullName;
}
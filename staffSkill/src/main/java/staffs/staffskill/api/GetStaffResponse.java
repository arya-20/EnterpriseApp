package staffs.staffskill.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //Needed for JSON
@Setter
@NoArgsConstructor //For model mapper
public class GetStaffResponse {
    private String staffId;
    private String name;
}
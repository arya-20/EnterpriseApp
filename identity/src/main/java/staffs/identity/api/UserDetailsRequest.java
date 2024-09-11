package staffs.identity.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDetailsRequest {
    private String username;
    private String password;
}
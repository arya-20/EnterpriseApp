package staffs.identity.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import staffs.identity.application.UserService;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class IdentityController {
    private UserService userService;

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody UserDetailsRequest command){

        Optional<String> token =
                userService.authenticate(command.getUsername(),
                        command.getPassword());

        return token.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .body("Invalid details provided"));
    }
}
// Post req to http://localhost:9000/validate
//{
//    "username": "manager",
//    "password": "password"
//}
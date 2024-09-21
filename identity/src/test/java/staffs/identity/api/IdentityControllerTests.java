package staffs.identity.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import staffs.identity.application.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class IdentityControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private IdentityController identityController;
    private UserDetailsRequest validRequest;
    private UserDetailsRequest invalidRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validRequest = new UserDetailsRequest();
        validRequest.setUsername("manager");
        validRequest.setPassword("password");

        invalidRequest = new UserDetailsRequest();
        invalidRequest.setUsername("invalidUser");
        invalidRequest.setPassword("invalidPassword");
    }

    @Test
    @DisplayName("Should return OK and token when valid credentials are provided")
    void test01() {
        String expectedToken = "validToken";
        when(userService.authenticate(validRequest.getUsername(), validRequest.getPassword()))
                .thenReturn(Optional.of(expectedToken));

        ResponseEntity<?> response = identityController.validate(validRequest);

        assertEquals(ResponseEntity.ok(expectedToken), response);
    }

    @Test
    @DisplayName("Should return Bad Request when invalid credentials are provided")
    void test02() {
        when(userService.authenticate(invalidRequest.getUsername(), invalidRequest.getPassword()))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = identityController.validate(invalidRequest);

        assertEquals(ResponseEntity.badRequest().body("Invalid details provided"), response);
    }
}
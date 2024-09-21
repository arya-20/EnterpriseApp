package staffs.staffskill.application;

import example.common.application.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IdentityServiceTest {

    private JwtTokenUtil jwtTokenUtil;
    private IdentityService identityService;

    @BeforeEach
    void setUp() {
        //mock
        jwtTokenUtil = mock(JwtTokenUtil.class);
        identityService = new IdentityService(jwtTokenUtil);
    }

    @Test
    @DisplayName("Should return true if token belongs to specified user")
    void test01() {
        //checks if userid matches expected id
        String token = "valid";
        String userId = "1";

        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.ID)).thenReturn(userId);

        boolean result = identityService.isSpecifiedUser(token, userId);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false if token does not belong to specified user")
    void test02() {
        //return false if token doesnt match
        String token = "valid";
        String userId = "1";

        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.ID)).thenReturn("99");

        boolean result = identityService.isSpecifiedUser(token, userId);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should throw JwtException if token is expired")
    void test03() {
        String token = "expiredToken";

        when(jwtTokenUtil.isTokenExpired(token)).thenReturn(true);

        assertThrows(JwtException.class, () -> {
            identityService.isSpecifiedUser(token, "1");
        });
    }

    @Test
    @DisplayName("Should return true if user is admin")
    void test04() {
        String token = "validToken";
        String role = "{key=ADMIN}";

        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.ROLE)).thenReturn(role);

        boolean result = identityService.isAdmin(token);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false if user is not admin")
    void test05() {
        String token = "validToken";
        String role = "{key=USER}";

        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.ROLE)).thenReturn(role);

        boolean result = identityService.isAdmin(token);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should throw JwtException if admin token is expired")
    void test06() {
        String token = "expiredToken";

        when(jwtTokenUtil.isTokenExpired(token)).thenReturn(true);

        assertThrows(JwtException.class, () -> {
            identityService.isAdmin(token);
        });
    }

    @Test
    @DisplayName("Should retrieve user details from token")
    void test07() {
        String token = "validToken";

        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.ID)).thenReturn("1");
        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.USERNAME)).thenReturn("username");
        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.PASSWORD)).thenReturn("password");
        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.ADDRESS)).thenReturn("address");
        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.FIRST_NAME)).thenReturn("test");
        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.SURNAME)).thenReturn("test");
        when(jwtTokenUtil.getClaimFromToken(token, UserDTO.ROLE)).thenReturn("{key=USER}");

        UserDTO userDTO = identityService.getDetailsFromToken(token);

        assertEquals("1", userDTO.getId());
        assertEquals("username", userDTO.getUserName());
        assertEquals("password", userDTO.getPassword());
        assertEquals("address", userDTO.getAddress());
        assertEquals("test", userDTO.getFirstName());
        assertEquals("test", userDTO.getSurname());
        assertEquals("{key=USER}", userDTO.getRole());
    }
}

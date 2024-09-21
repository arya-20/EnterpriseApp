package staffs.identity.application;

import example.common.application.JwtTokenUtil;
import example.common.infrastructure.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import staffs.identity.infrastructure.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        jwtTokenUtil = mock(JwtTokenUtil.class);
        userService = new UserService(userRepository, jwtTokenUtil);
    }

    @Test
    @DisplayName("Should return a token when valid credentials are provided")
    void test01() {
        String username = "testUser";
        String password = "testPassword";
        AppUser mockUser = new AppUser();
        when(userRepository.findUserByUsernameAndPassword(username, password))
                .thenReturn(Optional.of(mockUser));
        when(jwtTokenUtil.generateToken(mockUser)).thenReturn("mockToken");

        Optional<String> result = userService.authenticate(username, password);

        assertTrue(result.isPresent());
        assertEquals("mockToken", result.get());
        verify(userRepository, times(1)).findUserByUsernameAndPassword(username, password);
        verify(jwtTokenUtil, times(1)).generateToken(mockUser);
    }

    @Test
    @DisplayName("Should return an empty Optional when invalid credentials are provided")
    void test02() {
        String username = "testUser";
        String password = "wrongPassword";
        when(userRepository.findUserByUsernameAndPassword(username, password))
                .thenReturn(Optional.empty());

        Optional<String> result = userService.authenticate(username, password);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findUserByUsernameAndPassword(username, password);
        verify(jwtTokenUtil, times(0)).generateToken(any(AppUser.class));
    }
}

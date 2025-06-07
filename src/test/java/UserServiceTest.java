
import com.example.moviebooking.model.User;
import com.example.moviebooking.repository.UserRepository;
import com.example.moviebooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User();
        newUser.setUsername("alice");
        newUser.setEmail("alice@example.com");
        newUser.setPassword("plainPassword");
    }

    @Test
    @DisplayName("registerNewUser: успешная регистрация нового пользователя")
    void registerNewUser_success() {
        when(userRepository.findByUsername("alice")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = userService.registerNewUser(newUser);

        assertThat(saved.getPassword()).isEqualTo("encodedPassword");
        assertThat(captor.getValue().getPassword()).isEqualTo("encodedPassword");
        assertThat(saved.getUsername()).isEqualTo("alice");
        assertThat(saved.getEmail()).isEqualTo("alice@example.com");

        verify(userRepository, times(1)).findByUsername("alice");
        verify(userRepository, times(1)).findByEmail("alice@example.com");
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("registerNewUser: дублирующий username – кидает RuntimeException")
    void registerNewUser_usernameTaken_throwsException() {
        User existing = new User();
        existing.setUsername("alice");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> userService.registerNewUser(newUser))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Login '" + "alice" + "' already taken");

        verify(userRepository, times(1)).findByUsername("alice");
        verify(userRepository, never()).findByEmail(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("registerNewUser: дублирующий email – кидает RuntimeException")
    void registerNewUser_emailTaken_throwsException() {
        User existingByUsername = new User();
        existingByUsername.setUsername("bob");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(existingByUsername));

        assertThatThrownBy(() -> userService.registerNewUser(newUser))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email '" + "alice@example.com"  + "' already taken");

        verify(userRepository, times(1)).findByUsername("alice");
        verify(userRepository, times(1)).findByEmail("alice@example.com");
        verify(userRepository, never()).save(any());
    }
}

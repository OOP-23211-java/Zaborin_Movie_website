
import com.example.moviebooking.model.User;
import com.example.moviebooking.repository.UserRepository;
import com.example.moviebooking.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setUsername("john_doe");
        sampleUser.setPassword("hashed_password");
        sampleUser.setEmail("john@example.com");
    }

    @Test
    @DisplayName("loadUserByUsername: корректный пользователь возвращает UserDetails")
    void loadUserByUsername_existingUser_returnsUserDetails() {
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(sampleUser));


        UserDetails userDetails = customUserDetailsService.loadUserByUsername("john_doe");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("john_doe");
        assertThat(userDetails.getPassword()).isEqualTo("hashed_password");
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_USER");

        verify(userRepository, times(1)).findByUsername("john_doe");
    }

    @Test
    @DisplayName("loadUserByUsername: несуществующий пользователь – кидает исключение")
    void loadUserByUsername_userNotFound_throwsException() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                customUserDetailsService.loadUserByUsername("unknown")
        ).isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User 'unknown' not found");

        verify(userRepository, times(1)).findByUsername("unknown");
    }
}
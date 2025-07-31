package es.inditex.inditexapi.prices.application.usecase;

import es.inditex.inditexapi.security.application.dto.AuthLoginRequest;
import es.inditex.inditexapi.security.application.port.out.SecurityRepository;
import es.inditex.inditexapi.security.application.usecase.SecurityUseCaseImpl;
import es.inditex.inditexapi.security.domain.model.Role;
import es.inditex.inditexapi.security.domain.model.User;
import es.inditex.inditexapi.security.infrastructure.jwt.JwtUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("SecurityUseCase Unit Tests")
class SecurityUseCaseImplTest {

    @Mock
    private SecurityRepository securityRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SecurityUseCaseImpl securityUseCase;

    @Test
    @DisplayName("Login exitoso debe devolver un AuthResponse con token")
    void successfulLogin_shouldReturnAuthResponseWithToken() {
        // Arrange
        String username = "admin";
        String password = "123";
        User user = new User(username, "encodedPassword", true, true, true, true, Collections.singleton(new Role("ADMIN", Collections.emptySet())));
        AuthLoginRequest request = new AuthLoginRequest(username, password);

        when(securityRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);
        when(jwtUtils.createToken(any(Authentication.class))).thenReturn("fake.jwt.token");

        // Act
        var response = securityUseCase.login(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.jwt()).isEqualTo("fake.jwt.token");
        assertThat(response.username()).isEqualTo(username);
    }

    @Test
    @DisplayName("Login con contraseña incorrecta debe lanzar BadCredentialsException")
    void loginWithWrongPassword_shouldThrowBadCredentialsException() {
        // Arrange
        String username = "admin";
        String password = "wrongPassword";
        User user = new User(username, "encodedPassword", true, true, true, true, Collections.emptySet());
        AuthLoginRequest request = new AuthLoginRequest(username, password);

        when(securityRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> securityUseCase.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("La contraseña proporcionada es incorrecta.");
    }

    @Test
    @DisplayName("Login con usuario no existente debe lanzar UsernameNotFoundException")
    void loginWithNonExistentUser_shouldThrowUsernameNotFoundException() {
        // Arrange
        String username = "nonexistent";
        AuthLoginRequest request = new AuthLoginRequest(username, "password");

        when(securityRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> securityUseCase.login(request))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}

package es.inditex.inditexapi.security.application.port.in;

import es.inditex.inditexapi.security.application.dto.AuthLoginRequest;
import es.inditex.inditexapi.security.application.dto.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityUseCase extends UserDetailsService {

    AuthResponse login(AuthLoginRequest authLoginRequest);
    @Override
    UserDetails loadUserByUsername(String username);
}

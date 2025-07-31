package es.inditex.inditexapi.security.application.usecase;

import es.inditex.inditexapi.security.application.dto.AuthLoginRequest;
import es.inditex.inditexapi.security.application.dto.AuthResponse;
import es.inditex.inditexapi.security.application.port.in.SecurityUseCase;
import es.inditex.inditexapi.security.application.port.out.SecurityRepository;
import es.inditex.inditexapi.security.domain.model.User;
import es.inditex.inditexapi.security.infrastructure.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SecurityUseCaseImpl implements SecurityUseCase {

    private final SecurityRepository securityRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = securityRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario '" + username + "' no fue encontrado."));
        var authorities = user.getRoles().stream()
                .flatMap(role -> Stream.concat(
                        Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getName())),
                        role.getPermissions().stream().map(p -> new SimpleGrantedAuthority(p.getName()))
                ))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNoExpired(),
                user.isCredentialNoExpired(),
                user.isAccountNoLocked(),
                authorities);
    }

    @Override
    public AuthResponse login(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponse(username, "User logged successfully", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("La contraseña proporcionada es incorrecta.");
        }

        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }
}
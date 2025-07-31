package es.inditex.inditexapi.security.infrastructure.adapter.in.web;

import es.inditex.inditexapi.security.application.dto.AuthLoginRequest;
import es.inditex.inditexapi.security.application.dto.AuthResponse;
import es.inditex.inditexapi.security.application.port.in.SecurityUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "1. Authentication", description = "Endpoint para la autenticación de usuarios y obtención de token JWT.")
public class AuthenticationController {

    private final SecurityUseCase securityUseCase;

    @Operation(
            summary = "Login de Usuario",
            description = "Autentica a un usuario con su 'username' y 'password' y devuelve un token JWT si las credenciales son correctas."
    )
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return ResponseEntity.ok(this.securityUseCase.login(userRequest));
    }
}

package es.inditex.inditexapi.shared.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API INDITEX - Examen",
                description = "API para la consulta de precios aplicables, desarrollada bajo Arquitectura Hexagonal.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Hans Arancibia",
                        email = "hans.arancibia@gmail.com"
                ),
                license = @License(
                        name = "Uso exclusivo para evaluación técnica.",
                        url = "https://www.inditex.com"
                )
        ),
        servers = {
                @Server(
                        description = "Entorno Local",
                        url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(
                name = "bearerAuth"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Introduce el token JWT para autorizar las peticiones.",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT",
        paramName = HttpHeaders.AUTHORIZATION
)
public class SwaggerConfiguration {
}

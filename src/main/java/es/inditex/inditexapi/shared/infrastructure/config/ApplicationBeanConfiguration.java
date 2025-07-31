package es.inditex.inditexapi.shared.infrastructure.config;

import es.inditex.inditexapi.prices.application.mapper.PriceMapper;
import es.inditex.inditexapi.prices.application.port.in.GetPriceUseCase;
import es.inditex.inditexapi.prices.application.port.out.PriceRepository;
import es.inditex.inditexapi.prices.application.usecase.GetPriceUseCaseImpl;
import es.inditex.inditexapi.security.application.port.in.SecurityUseCase;
import es.inditex.inditexapi.security.application.port.out.SecurityRepository;
import es.inditex.inditexapi.security.application.usecase.SecurityUseCaseImpl;
import es.inditex.inditexapi.security.infrastructure.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public GetPriceUseCase getPriceUseCase(PriceRepository priceRepository, PriceMapper priceMapper) {
        return new GetPriceUseCaseImpl(priceRepository, priceMapper);
    }

    @Bean
    public SecurityUseCase securityUseCase(SecurityRepository securityRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        return new SecurityUseCaseImpl(securityRepository, jwtUtils, passwordEncoder);
    }
}
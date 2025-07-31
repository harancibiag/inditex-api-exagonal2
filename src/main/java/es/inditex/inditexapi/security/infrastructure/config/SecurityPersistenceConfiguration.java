package es.inditex.inditexapi.security.infrastructure.config;

import es.inditex.inditexapi.security.application.port.out.SecurityRepository;
import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.SecurityJpaAdapter;
import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.mapper.SecurityPersistenceMapper;
import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityPersistenceConfiguration {

    @Bean
    public SecurityRepository securityRepository(UserJpaRepository userJpaRepository, SecurityPersistenceMapper mapper) {
        return new SecurityJpaAdapter(userJpaRepository, mapper);
    }
}
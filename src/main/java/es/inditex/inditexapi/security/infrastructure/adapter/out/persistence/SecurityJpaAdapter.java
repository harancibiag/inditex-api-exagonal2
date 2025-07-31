package es.inditex.inditexapi.security.infrastructure.adapter.out.persistence;

import es.inditex.inditexapi.security.application.port.out.SecurityRepository;
import es.inditex.inditexapi.security.domain.model.User;
import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.mapper.SecurityPersistenceMapper;
import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityJpaAdapter implements SecurityRepository {

    private final UserJpaRepository userJpaRepository;
    private final SecurityPersistenceMapper mapper;

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userJpaRepository.findUserEntityByUsername(username)
                .map(mapper::toDomain);
    }
}
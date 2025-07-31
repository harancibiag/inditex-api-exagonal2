package es.inditex.inditexapi.security.application.port.out;

import es.inditex.inditexapi.security.domain.model.User;
import java.util.Optional;

public interface SecurityRepository {
    Optional<User> findUserByUsername(String username);
}
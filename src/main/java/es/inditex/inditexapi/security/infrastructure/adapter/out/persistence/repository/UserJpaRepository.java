package es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.repository;

import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
}
package es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.mapper;

import es.inditex.inditexapi.security.domain.model.Permission;
import es.inditex.inditexapi.security.domain.model.Role;
import es.inditex.inditexapi.security.domain.model.User;
import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.entity.PermissionEntity;
import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.entity.RoleEntity;
import es.inditex.inditexapi.security.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SecurityPersistenceMapper {

    @Mapping(target = "roles", qualifiedByName = "rolesEntityToRoles")
    User toDomain(UserEntity userEntity);

    @Named("rolesEntityToRoles")
    default Set<Role> rolesEntityToRoles(Set<RoleEntity> roles) {
        return roles.stream()
                .map(this::roleEntityToRole)
                .collect(Collectors.toSet());
    }

    @Mapping(source = "roleEnum", target = "name")
    @Mapping(source = "permissionList", target = "permissions", qualifiedByName = "permissionsEntityToPermissions")
    Role roleEntityToRole(RoleEntity roleEntity);

    @Named("permissionsEntityToPermissions")
    default Set<Permission> permissionsEntityToPermissions(Set<PermissionEntity> permissions) {
        return permissions.stream()
                .map(this::permissionEntityToPermission)
                .collect(Collectors.toSet());
    }

    Permission permissionEntityToPermission(PermissionEntity permissionEntity);
}

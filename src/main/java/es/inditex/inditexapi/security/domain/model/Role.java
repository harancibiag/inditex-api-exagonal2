package es.inditex.inditexapi.security.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Role {
    private String name;
    private Set<Permission> permissions;
}

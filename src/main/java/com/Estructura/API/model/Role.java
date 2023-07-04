package com.Estructura.API.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.Estructura.API.model.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_CREATE,
                    ADMIN_DELETE
            )
    ),
    CUSTOMER(
            Collections.emptySet()
    ),
    RETAILOWNER(Collections.emptySet())
    ;

    @Getter
    private final Set<Permission> permissions;

    public  List<SimpleGrantedAuthority> getAuthorities(){
        var authorities= getPermissions()
                .stream()
                .map(permission->new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(""+ this.name()));
        return  authorities;
    }
}



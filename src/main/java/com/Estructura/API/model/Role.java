package com.Estructura.API.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.Estructura.API.model.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
        Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_CREATE,
            ADMIN_DELETE
        )),
    CUSTOMER(
        Collections.emptySet()),
    // Service Providers
    CONSTRUCTIONCOMPANY(
        Collections.emptySet()),
    ELECTRICIAN(
        Collections.emptySet()),
    RENTER(
        Collections.emptySet()),
    RETAILSTORE(
        Collections.emptySet()),
    // Professionals
    ARCHITECT(
        Collections.emptySet()),
    CARPENTER(
        Collections.emptySet()),
    INTERIORDESIGNER(
        Collections.emptySet()),
    LANDSCAPEARCHITECT(
        Collections.emptySet()),
    MASONWORKER(
        Collections.emptySet()),
    PAINTER(
        Collections.emptySet());

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(
                permission.getPermission()))
            .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}

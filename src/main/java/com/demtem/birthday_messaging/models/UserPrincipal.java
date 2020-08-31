package com.demtem.birthday_messaging.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;


public class UserPrincipal implements OAuth2User, UserDetails {

    private String _id;
    private String email;
    private String firstName;
    private String lastName;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(String _id, String email, String firstName, String lastName,
                         Collection<? extends GrantedAuthority> authorities) {
        this._id = _id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role
                -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

        return new UserPrincipal(
                user.get_id(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                authorities
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public String get_id() {
        return _id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(_id);
    }
}
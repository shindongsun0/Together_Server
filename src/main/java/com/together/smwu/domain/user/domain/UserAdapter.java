package com.together.smwu.domain.user.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserAdapter implements UserDetails {

    private Long id;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserAdapter(Long id, String name, Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.name = name;
        this.authorities = authorities;
    }

    public static UserAdapter create(User user){
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserAdapter(
                user.getUserId(),
                user.getName(),
                authorities
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public static UserAdapter create(User user, Map<String, Object> attributes) {
        UserAdapter userAdapter = UserAdapter.create(user);
        userAdapter.setAttributes(attributes);
        return userAdapter;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setAttributes(Map<String, Object> attributes){
        this.attributes = attributes;
    }
}

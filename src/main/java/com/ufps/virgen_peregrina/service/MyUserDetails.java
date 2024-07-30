package com.ufps.virgen_peregrina.service;

import com.ufps.virgen_peregrina.entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class MyUserDetails implements UserDetails {

    private Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();


        authorities.add(new SimpleGrantedAuthority(usuario.getRol().getNombre()));

        return authorities;
    }


    @Override
    public String getPassword() { return usuario.getClave(); }

    @Override
    public String getUsername() {
        return usuario.getCorreo();
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

        return usuario.getEstado() == 1;
    }
}
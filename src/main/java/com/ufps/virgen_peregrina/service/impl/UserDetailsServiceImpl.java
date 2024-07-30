package com.ufps.virgen_peregrina.service.impl;

import com.ufps.virgen_peregrina.entity.Usuario;
import com.ufps.virgen_peregrina.repository.UsuarioRepository;
import com.ufps.virgen_peregrina.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.getUsuarioByCorreo(username);


        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return new MyUserDetails(usuario);
    }
}
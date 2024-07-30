package com.ufps.virgen_peregrina.service.impl;

import com.ufps.virgen_peregrina.entity.Rol;
import com.ufps.virgen_peregrina.entity.Token;
import com.ufps.virgen_peregrina.entity.Usuario;
import com.ufps.virgen_peregrina.repository.RolRepository;
import com.ufps.virgen_peregrina.repository.TokenRepository;
import com.ufps.virgen_peregrina.repository.UsuarioRepository;
import com.ufps.virgen_peregrina.service.MailService;
import com.ufps.virgen_peregrina.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    MailService mailService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean restablecerContraseña(String correo) {
        try {
            Usuario usuario = usuarioRepository.getUsuarioByCorreo(correo);
            Token token = new Token();
            String codigo = UUID.randomUUID().toString();
            token.setCodigo(codigo);
            token.setUsuario(usuario);
            token.setEstado(1);
            token.setTipo(1);

            tokenRepository.save(token);

            String url = "https://virgenperegrina-production.up.railway.app/cambiar/" + token.getCodigo();
            String cuerpo = "Por favor, restablezca su contraseña en el siguiente enlace: <a href=\"" + url + "\">" + url + "</a>";

            mailService.enviarCorreo(usuario.getCorreo(), "RESTABLECER SU CONTRASEÑA DE SU CUENTA VIRGEN PEREGRINA", cuerpo);


            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        }
    }

    @Override
    public boolean cambiarContraseña(Usuario usuario, String clave) {
        usuario.setClave(passwordEncoder.encode(clave));
        usuarioRepository.save(usuario);

        return true;
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.getUsuarioByCorreo(correo);
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        try {
            usuario.setRol(new Rol(1, "USUARIO", 1));
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
            usuario.setEstado(0);
            usuarioRepository.save(usuario);

            Token token = new Token();
            String codigo = UUID.randomUUID().toString();
            token.setCodigo(codigo);
            token.setUsuario(usuario);
            token.setEstado(1);
            token.setTipo(0);

            tokenRepository.save(token);
            String url = "https://virgenperegrina-production.up.railway.app/activar/" + token.getCodigo();
            String cuerpo = "Por favor, confirme su cuenta en el siguiente enlace: <a href=\"" + url + "\">" + url + "</a>";

            mailService.enviarCorreo(usuario.getCorreo(), "ENLACE DE ACTIVACIÓN DE SU CUENTA VIRGEN PEREGRINA", cuerpo);


            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        }

    }

    @Override
    public Page<Usuario> obtenerUsuariosParaAdmin(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Usuario> obtenerUsuariosParaSuperadmin(Pageable pageable) {
        return usuarioRepository.findByEnabledTrueAndRoleNotSuperAdmin(pageable);
    }


    @Override
    public boolean actualizarUsuario(Usuario usuario) {

        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public boolean bloquearUsuario(int id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.setEstado(0);
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public boolean actualizarRol(int id, int idRol) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        Rol rol = rolRepository.getReferenceById(idRol);
        usuario.setRol(rol);
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepository.getReferenceById(id);
    }
}
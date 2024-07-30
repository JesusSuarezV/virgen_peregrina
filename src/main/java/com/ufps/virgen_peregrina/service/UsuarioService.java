package com.ufps.virgen_peregrina.service;

import com.ufps.virgen_peregrina.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    public boolean restablecerContraseña(String correo);

    public boolean cambiarContraseña(Usuario usuario, String clave);

    public Usuario obtenerUsuarioPorCorreo(String correo);

    public boolean registrarUsuario(Usuario usuario);

    public Page<Usuario> obtenerUsuariosParaAdmin(Pageable pageable);

    public Page<Usuario> obtenerUsuariosParaSuperadmin(Pageable pageable);

    public boolean actualizarUsuario(Usuario usuario);

    public boolean bloquearUsuario(int id);

    public boolean actualizarRol (int id, int idRol);

    Usuario obtenerUsuarioPorId(int id);
}

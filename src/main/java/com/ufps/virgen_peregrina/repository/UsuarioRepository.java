package com.ufps.virgen_peregrina.repository;

import com.ufps.virgen_peregrina.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario getUsuarioByCorreo(String correo);


    @Query("SELECT u FROM Usuario u " +
            "WHERE u.estado = 1 " +
            "AND u.rol.nombre NOT IN ('SUPERADMINISTRADOR') " +
            "ORDER BY u.nombre ASC")
    Page<Usuario> findByEnabledTrueAndRoleNotSuperAdmin(Pageable pageable);

}

package com.ufps.virgen_peregrina.repository;

import com.ufps.virgen_peregrina.entity.Replica;
import com.ufps.virgen_peregrina.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplicaRepository extends JpaRepository<Replica, Integer> {

    Page<Replica> findByEstadoOrderByNombre(int estado, Pageable pageable);
    @Query("SELECT r FROM Replica r WHERE r.estado != 0 AND r.estado != 1 ORDER BY r.nombre")
    Page<Replica> findReplicas(Pageable pageable);
    @Query("SELECT r FROM Replica r WHERE r.estado != 0 AND r.estado != 1 AND r.usuario = :usuario ORDER BY r.nombre")
    Page<Replica> findMisReplicas(@Param("usuario") Usuario usuario, Pageable pageable);

}

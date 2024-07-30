package com.ufps.virgen_peregrina.repository;

import com.ufps.virgen_peregrina.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}

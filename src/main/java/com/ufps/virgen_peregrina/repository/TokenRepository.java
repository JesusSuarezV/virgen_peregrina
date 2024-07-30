package com.ufps.virgen_peregrina.repository;

import com.ufps.virgen_peregrina.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}

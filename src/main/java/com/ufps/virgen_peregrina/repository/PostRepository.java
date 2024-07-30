package com.ufps.virgen_peregrina.repository;

import com.ufps.virgen_peregrina.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByEstadoOrderByCreacionDesc(int estado, Pageable pageable);

}

package com.ufps.virgen_peregrina.service;

import com.ufps.virgen_peregrina.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    public void crearPost(Post post);

    public Page<Post> verPosts(Pageable pageable);

    public Post obtenerPost(int id);

    public void actualizarPost(Post post);

    public void eliminarPost(Post post);

}

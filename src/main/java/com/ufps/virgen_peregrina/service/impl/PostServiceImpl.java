package com.ufps.virgen_peregrina.service.impl;

import com.ufps.virgen_peregrina.entity.Post;
import com.ufps.virgen_peregrina.repository.PostRepository;
import com.ufps.virgen_peregrina.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public void crearPost(Post post) {
        post.setEstado(1);
        postRepository.save(post);

    }

    @Override
    public Page<Post> verPosts(Pageable pageable) {
        return postRepository.findByEstadoOrderByCreacionDesc(1, pageable);
    }

    @Override
    public Post obtenerPost(int id) {
        return postRepository.getReferenceById(id);
    }

    @Override
    public void actualizarPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void eliminarPost(Post post) {
        post.setEstado(0);
        postRepository.save(post);

    }
}

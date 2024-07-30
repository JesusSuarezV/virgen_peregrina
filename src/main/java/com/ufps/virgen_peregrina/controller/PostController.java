package com.ufps.virgen_peregrina.controller;

import com.ufps.virgen_peregrina.entity.Post;
import com.ufps.virgen_peregrina.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public String verNoticias(Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("posts", postService.verPosts(PageRequest.of(page - 1, 3)));
        return "gestion_noticias/noticias";
    }

    @GetMapping("/crear")
    public String crearNoticia() {
        return "gestion_noticias/crear";

    }

    @PostMapping("/guardar")
    public String guardarNoticia(@RequestParam("titulo") String titulo, @RequestParam("contenido") String contenido, @RequestParam("imagen") MultipartFile imagen) throws IOException {
        try {

            Post post = new Post();
            post.setTitulo(titulo);
            post.setContenido(contenido);
            post.setCreacion(LocalDate.now());
            post.setImagen(imagen.getBytes());


            postService.crearPost(post);

            return "redirect:/posts?exitoPost";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/posts?errorImagen";
        }
    }


    @GetMapping("/{idPost}/editar")
    public String obtenerNoticia(Model model, @PathVariable int idPost) {
        model.addAttribute("post", postService.obtenerPost(idPost));
        return "gestion_noticias/editar";
    }

    @PostMapping("/{idPost}/actualizar")
    public String actualizarNoticia(@PathVariable int idPost, @RequestParam("titulo") String titulo, @RequestParam("contenido") String contenido, @RequestParam("imagen") MultipartFile imagen) throws IOException {
        try {
            Post post = postService.obtenerPost(idPost);
            post.setTitulo(titulo);
            post.setContenido(contenido);
            if (!imagen.isEmpty()) {
                post.setImagen(imagen.getBytes());
            }

            postService.actualizarPost(post);

            return "redirect:/posts?exitoEditar";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/posts?errorImagen";
        }

    }

    @GetMapping("/{idPost}")
    public String verNoticia(@PathVariable int idPost, Model model){
        model.addAttribute("post", postService.obtenerPost(idPost));
        return "gestion_noticias/noticia";
    }

    @PostMapping("/{idPost}/eliminar")
    public String eliminarNoticia(@PathVariable int idPost){
        postService.eliminarPost(postService.obtenerPost(idPost));
        return "redirect:/posts?exitoEliminar";
    }



}

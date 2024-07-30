package com.ufps.virgen_peregrina.controller;

import com.ufps.virgen_peregrina.entity.Token;
import com.ufps.virgen_peregrina.entity.Usuario;
import com.ufps.virgen_peregrina.repository.RolRepository;
import com.ufps.virgen_peregrina.service.SesionService;
import com.ufps.virgen_peregrina.service.TokenService;
import com.ufps.virgen_peregrina.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SesionService sesionService;

    @Autowired
    TokenService tokenService;

    @Autowired
    RolRepository rolRepository;

    @GetMapping
    public String index() {
        return "gestion_usuario/index";
    }

    @GetMapping("/iniciar_sesion")
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        return "gestion_usuario/login";
    }

    @GetMapping("/registrarse")
    public String registro() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "gestion_usuario/registro";
    }


    @PostMapping("/registrar_usuario")
    public String guardarUsuario(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("celular") long celular, @RequestParam("correo") String correo, @RequestParam("clave") String clave, @RequestParam("peregrino") boolean peregrino, @RequestParam("imagen") MultipartFile imagen) throws IOException {

        if (usuarioService.obtenerUsuarioPorCorreo(correo) != null) return "redirect:/registrarse?error";
        else {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setCelular(celular);
            usuario.setCorreo(correo);
            usuario.setClave(clave);
            usuario.setImagen(imagen.getBytes());
            usuario.setPeregrino(peregrino);
            usuarioService.registrarUsuario(usuario);
            return "redirect:/?exitoRegistro";
        }
    }

    @GetMapping("/activar/{codigo}")
    public String activarUsuario(@PathVariable String codigo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        Token token = tokenService.obtenerToken(codigo);
        if (token != null && token.getEstado() == 1 && token.getTipo() == 0) {
            Usuario usuario = token.getUsuario();
            usuario.setEstado(1);
            token.setEstado(0);
            tokenService.guardarToken(token);
            System.out.println("verificacion0");
            usuarioService.actualizarUsuario(usuario);
            System.out.println("verificacion1");
            return "redirect:/?exitoActivacion";

        } else {

            System.out.println("verificacion2");
            return "redirect:/?errorToken";
        }
    }


    @GetMapping("/perfil")
    public String editarUsuario(Model model) {
        model.addAttribute("usuario", usuarioService.obtenerUsuarioPorCorreo(sesionService.getUsernameFromSession()));
        return "gestion_usuario/perfil";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("celular") long celular, @RequestParam("imagen") MultipartFile imagen, @RequestParam("peregrino") boolean peregrino) throws IOException {
        try {


            Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(sesionService.getUsernameFromSession());
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setCelular(celular);
            if (!imagen.isEmpty()) {
                usuario.setImagen(imagen.getBytes());
            }
            usuario.setPeregrino(peregrino);
            usuarioService.actualizarUsuario(usuario);

            return "redirect:/perfil?exito";
        } catch (Exception e) {
            return "redirect:/perfil?errorImagen";
        }

    }

    @GetMapping("/recuperar")
    public String recuperarClave() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "gestion_usuario/recuperar";
    }

    @PostMapping("/recuperar/enlace")
    public String generarEnlace(@RequestParam("correo") String correo) {

        if (usuarioService.obtenerUsuarioPorCorreo(correo) == null) return "redirect:/recuperar?error";
        else {
            usuarioService.restablecerContraseña(correo);
            return "redirect:/?exitoRestablecer";
        }

    }

    @GetMapping("/cambiar/{codigo}")
    public String cambiarClave(Model model, @PathVariable String codigo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        Token token = tokenService.obtenerToken(codigo);
        if (token != null && token.getEstado() == 1 && token.getTipo() == 1) {
            model.addAttribute("codigo", codigo);
            return "gestion_usuario/cambiar.html";

        } else return "redirect:/?errorToken";
    }

    @PostMapping("/actualizar/{codigo}")
    public String actualizarClave(@PathVariable String codigo, @RequestParam("clave") String clave) {
        Token token = tokenService.obtenerToken(codigo);
        usuarioService.cambiarContraseña(token.getUsuario(), clave);
        token.setEstado(0);
        tokenService.guardarToken(token);
        return "redirect:/?exitoCambio";
    }

    @GetMapping("/usuarios")
    public String verUsuarios(Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("usuarios", usuarioService.obtenerUsuariosParaSuperadmin(PageRequest.of(page - 1, 3)));
        return "gestion_usuario/usuarios.html";
    }

    @PostMapping("/usuarios/{idUsuario}/eliminar")
    public String bloquearUsuario(@PathVariable int idUsuario) {

        if (usuarioService.obtenerUsuarioPorCorreo(sesionService.getUsernameFromSession()) != null) {
            usuarioService.bloquearUsuario(idUsuario);
            return "redirect:/usuarios?exitoBloquear";

        }
        return "redirect:/usuarios?error";


    }

    @GetMapping("/usuarios/{idUsuario}/editar_rol")
    public String editarRol(@PathVariable int idUsuario, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        if (usuario != null && usuario.getEstado() != 0) {
            model.addAttribute("usuario", usuarioService.obtenerUsuarioPorId(idUsuario));
            return "gestion_usuario/rol.html";
        }
        return "redirect:/usuarios?error";
    }

    @PostMapping("/usuarios/{idUsuario}/actualizar_rol")
    public String actualizarRol(@PathVariable int idUsuario, @RequestParam("idRol") int idRol) {
        if (idRol == 3) return "redirect:/usuarios?error";
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        usuario.setRol(rolRepository.getReferenceById(idRol));
        usuarioService.actualizarUsuario(usuario);
        return "redirect:/usuarios?exitoRol";
    }


}






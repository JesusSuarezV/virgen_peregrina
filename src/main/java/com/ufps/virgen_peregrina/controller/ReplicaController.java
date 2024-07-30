package com.ufps.virgen_peregrina.controller;

import com.ufps.virgen_peregrina.entity.Replica;
import com.ufps.virgen_peregrina.entity.Usuario;
import com.ufps.virgen_peregrina.service.ReplicaService;
import com.ufps.virgen_peregrina.service.SesionService;
import com.ufps.virgen_peregrina.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/replicas")
public class ReplicaController {

    @Autowired
    ReplicaService replicaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SesionService sessionService;

    @GetMapping
    public String verReplicas(Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("replicas", replicaService.verReplicas(PageRequest.of(page - 1, 3)));
        return "gestion_replicas/replicas.html";

    }

    @GetMapping("/donar")
    public String donarReplica() {
        return "gestion_replicas/donar.html";

    }

    @PostMapping("/guardar")
    public String guardarReplica(@RequestParam("nombre") String nombre, @RequestParam("restauracion") boolean restauracion, @RequestParam("imagen") MultipartFile imagen) throws IOException {
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(sessionService.getUsernameFromSession());
        Replica replica = new Replica();
        replica.setNombre(nombre);
        replica.setRestauracion(restauracion);
        replica.setUsuario(usuario);
        replica.setImagen(imagen.getBytes());
        replicaService.crearReplica(replica);
        if (usuario.getRol().getId() == 1) return "redirect:/replicas?exitoDonarP";
        return "redirect:/replicas?exitoDonarA";

    }

    @GetMapping("/{idReplica}/editar")
    public String editarReplica(@PathVariable int idReplica, Model model) {
        model.addAttribute("replica", replicaService.obtenerReplica(idReplica));
        return "gestion_replicas/editar";
    }

    @PostMapping("/{idReplica}/actualizar")
    public String actualizarReplica(@PathVariable int idReplica, @RequestParam("nombre") String nombre, @RequestParam("recepcion")LocalDate recepcion, @RequestParam("imagen") MultipartFile imagen, @RequestParam("estado") int estado) throws IOException {
        Replica replica = replicaService.obtenerReplica(idReplica);
        replica.setNombre(nombre);
        replica.setRecepcion(recepcion);
        if(!imagen.isEmpty()){
            replica.setImagen(imagen.getBytes());
        }
        replica.setEstado(estado);
        replicaService.actualizarReplica(replica);
        return "redirect:/replicas?exitoActualizar";

    }

    @PostMapping("/{idReplica}/eliminar")
    public String eliminarReplica(@PathVariable int idReplica) {
        Replica replica = replicaService.obtenerReplica(idReplica);

        replicaService.eliinarReplica(replica);
        return "redirect:/replicas?exitoEliminar";

    }

    @GetMapping("/mis_replicas")
    public String verMisReplicas(Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("replicas", replicaService.verMisReplicas(usuarioService.obtenerUsuarioPorCorreo(sessionService.getUsernameFromSession()), PageRequest.of(page - 1, 3)));
        return "gestion_replicas/donadas.html";

    }

    @GetMapping("/aprobar")
    public String verReplicasAprobar(Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("replicas",replicaService.verReplicasPorAprobar(PageRequest.of(page - 1, 3)));
    return "gestion_replicas/aprobar.html";

    }

    @PostMapping("/aprobar/{idReplica}")
    public String aprobarReplica(@PathVariable int idReplica) {
        Replica replica = replicaService.obtenerReplica(idReplica);

        replicaService.aprobarReplica(replica);
        return "redirect:/replicas?exitoAprobar";

    }

    @PostMapping("/aprobar/{idReplica}/eliminar")
    public String rechazarReplica(@PathVariable int idReplica) {
        Replica replica = replicaService.obtenerReplica(idReplica);

        replicaService.eliinarReplica(replica);
        return "redirect:/replicas?exitoEliminar";

    }





}

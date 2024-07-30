package com.ufps.virgen_peregrina.service.impl;

import com.ufps.virgen_peregrina.entity.Replica;
import com.ufps.virgen_peregrina.entity.Usuario;
import com.ufps.virgen_peregrina.repository.ReplicaRepository;
import com.ufps.virgen_peregrina.service.MailService;
import com.ufps.virgen_peregrina.service.ReplicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReplicaServiceImpl implements ReplicaService {
    @Autowired
    ReplicaRepository replicaRepository;

    @Autowired
    MailService mailService;

    @Override
    public Page<Replica> verReplicas(Pageable pageable) {
        return replicaRepository.findReplicas(pageable);
    }

    @Override
    public Page<Replica> verMisReplicas(Usuario usuario, Pageable pageable) {
        return replicaRepository.findMisReplicas(usuario, pageable);
    }

    @Override
    public Page<Replica> verReplicasPorAprobar(Pageable pageable) {
        return replicaRepository.findByEstadoOrderByNombre(1, pageable);
    }

    @Override
    public void crearReplica(Replica replica) {

        if (replica.getUsuario().getRol().getId() == 1) {
            replica.setEstado(1);
        } else {
            replica.setRecepcion(LocalDate.now());
            if (replica.isRestauracion()) replica.setEstado(3);
            else replica.setEstado(4);
        }
        replicaRepository.save(replica);

    }

    @Override
    public Replica obtenerReplica(int id) {
        return replicaRepository.getReferenceById(id);
    }

    @Override
    public void actualizarReplica(Replica replica) {
        replicaRepository.save(replica);
    }

    @Override
    public void aprobarReplica(Replica replica) {
        try {
            replica.setEstado(2);
            replicaRepository.save(replica);

            String cuerpo = "Su replica (" + replica.getNombre() + ") ha sido aprobada con exito, por favor dirijase con ella al despacho parroquial\nNombre: " + replica.getNombre() + "\nId:" + replica.getId();


            mailService.enviarCorreo(replica.getUsuario().getCorreo(), "APROBACIÓN DE REPLICA - VIRGEN PEREGRINA", cuerpo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void eliinarReplica(Replica replica) {
        replica.setEstado(0);
        replicaRepository.save(replica);

    }
}

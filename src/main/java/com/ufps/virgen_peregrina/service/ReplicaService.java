package com.ufps.virgen_peregrina.service;

import com.ufps.virgen_peregrina.entity.Replica;
import com.ufps.virgen_peregrina.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ReplicaService {

    public Page<Replica> verReplicas(Pageable pageable);

    public Page<Replica> verMisReplicas(Usuario usuario, Pageable pageable);

    public Page<Replica> verReplicasPorAprobar(Pageable pageable);
    public void crearReplica(Replica replica);
    public Replica obtenerReplica(int id);
    public void actualizarReplica(Replica replica);
    public void aprobarReplica(Replica replica);
    public void eliinarReplica(Replica replica);




}

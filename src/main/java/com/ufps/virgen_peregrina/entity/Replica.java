package com.ufps.virgen_peregrina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Base64;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Replica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private byte[] imagen;
    private LocalDate recepcion;
    private boolean restauracion;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private int estado;
    public String getImagenBase64() {
        if (imagen != null && imagen.length > 0) {
            return Base64.getEncoder().encodeToString(imagen);
        } else {
            return "";
        }
    }
}

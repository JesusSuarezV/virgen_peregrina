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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;

    @Lob
    private String contenido;

    private LocalDate creacion;

    private byte[] imagen;

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

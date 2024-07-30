package com.ufps.virgen_peregrina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String apellido;

    private byte[] imagen;

    private String correo;

    private String clave;

    private boolean peregrino;

    private long celular;


    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    private int estado;

    public String getImagenBase64() {
        if (imagen != null && imagen.length > 0) {
            return Base64.getEncoder().encodeToString(imagen);
        } else {
            return "";
        }
    }


}

package com.ufps.virgen_peregrina.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    private String codigo;

    private int tipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private int estado;
}

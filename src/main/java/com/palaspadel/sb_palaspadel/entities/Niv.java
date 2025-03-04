package com.palaspadel.sb_palaspadel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter // Genera los getters
@Setter // Genera los setters
@Entity // Entidad de la base de datos
@Table(name = "niv") // Nombre de la tabla en la base de datos
public class Niv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Si es autoincremental
    @Column(name = "nivid", nullable = false)
    private Integer id;

    @Column(name = "nivnom", nullable = false)
    private String nivnom;

    @Lob // Para campos grandes
    @Column(name = "nivdes")
    private String nivdes;

    @Column(name = "nivfec", updatable = false)
    private Instant nivfec;

    // Antes de insertar en la base de datos
    @PrePersist
    protected void onCreate() {
        if (nivfec == null) {
            nivfec = Instant.now();
        }
    }
}

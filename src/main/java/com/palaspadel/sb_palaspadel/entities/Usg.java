package com.palaspadel.sb_palaspadel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "usg")
public class Usg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usgid", nullable = false)
    private Integer id;

    @Column(name = "usgnom", nullable = false, length = 50)
    private String usgnom;

    @Column(name = "usgdes")
    private String usgdes;

    @Column(name = "usgfec", nullable = false, updatable = false)
    private Instant usgfec;

    @Column(name = "usgact", nullable = false)
    private Boolean usgact = true;

    @PrePersist
    protected void prePersist() {
        if (usgfec == null) {
            usgfec = Instant.now();
        }
        if (usgact == null) {
            usgact = true;
        }
    }
}

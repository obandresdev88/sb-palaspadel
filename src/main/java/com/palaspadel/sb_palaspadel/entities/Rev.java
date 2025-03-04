package com.palaspadel.sb_palaspadel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "rev", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"revusu", "revpal"}) // Un usuario solo puede reseñar una pala una vez
})
public class Rev {
    @EmbeddedId
    private RevId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("revusu") // Mapea este campo con la clave compuesta
    @JoinColumn(name = "revusu", nullable = false)
    private Usu revusu;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("revpal") // Mapea este campo con la clave compuesta
    @JoinColumn(name = "revpal", nullable = false)
    private Pal revpal;

    @Column(name = "revval", nullable = false)
    private Byte revval = 3;

    @Column(name = "revopi")
    private String revopi;

    @Column(name = "revfec", nullable = false, updatable = false)
    private Instant revfec;

    @PrePersist
    protected void prePersist() {
        if (revval == null) {
            revval = 3;
        }
        if (revfec == null) {
            revfec = Instant.now();
        }
    }
}

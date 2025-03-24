package com.palaspadel.sb_palaspadel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "usu_usg")
public class UsuUsg {
    @EmbeddedId
    private UsuUsgId id;

    @MapsId("usuusuid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuusuid", nullable = false)
    private Usu usuario;

    @MapsId("usuusgid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuusgid", nullable = false)
    private Usg permiso;

    @Column(name = "usuusfec", nullable = false, updatable = false)
    private Instant usuusfec;

    @PrePersist
    protected void prePersist() {
        if (usuusfec == null) {
            usuusfec = Instant.now();
        }
    }
}

package com.palaspadel.sb_palaspadel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "usu", uniqueConstraints = @UniqueConstraint(columnNames = "usuema"))
public class Usu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuid", nullable = false)
    private Integer id;

    @Column(name = "usunom", nullable = false, length = 75)
    private String usunom;

    @Column(name = "usuema", nullable = false, unique = true)
    private String usuema;

    @Column(name = "usupas", nullable = false)
    private String usupas;

    @Column(name = "usufec", updatable = false)
    private Instant usufec;

    @ManyToOne
    @JoinColumn(name = "usuniv", referencedColumnName = "nivid")
    private Niv nivel; // Relación con la entidad Niv

    @PrePersist
    protected void onCreate() {
        if (usufec == null) {
            usufec = Instant.now();
        }
    }
}

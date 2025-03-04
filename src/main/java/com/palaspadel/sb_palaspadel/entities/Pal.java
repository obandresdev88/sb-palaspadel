package com.palaspadel.sb_palaspadel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "pal")
public class Pal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "palid", nullable = false)
    private Integer id;

    @Column(name = "palmar", nullable = false, length = 50)
    private String palmar;

    @Column(name = "palmod", nullable = false, length = 75)
    private String palmod;

    @Column(name = "palpes", nullable = false)
    private Integer palpes;

    @Enumerated(EnumType.STRING)
    @Column(name = "palfor", nullable = false)
    private FormaPala palfor = FormaPala.REDONDA;

    @Enumerated(EnumType.STRING)
    @Column(name = "paldureza", nullable = false)
    private DurezaPala paldureza = DurezaPala.MEDIA;

    @Enumerated(EnumType.STRING)
    @Column(name = "palbal", nullable = false)
    private BalancePala palbal = BalancePala.MEDIO;

    @Column(name = "palpre", nullable = false, precision = 10, scale = 2)
    private BigDecimal palpre = BigDecimal.ZERO;

    @Column(name = "palurl", nullable = false)
    private String palurl = "";

    @PrePersist
    protected void prePersist() {
        if (palpre == null) {
            palpre = BigDecimal.ZERO;
        }
        if (palurl == null) {
            palurl = "";
        }
    }

    // ENUMS para los atributos de la pala

    public enum FormaPala {
        REDONDA, LAGRIMA, DIAMANTE
    }

    public enum DurezaPala {
        BLANDA, MEDIA, DURA
    }

    public enum BalancePala {
        BAJO, MEDIO, ALTO
    }
}

package com.palaspadel.sb_palaspadel.entities;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class RevId implements Serializable {
    private Integer revusu;
    private Integer revpal;

    public RevId() {}

    public RevId(Integer revusu, Integer revpal) {
        this.revusu = revusu;
        this.revpal = revpal;
    }
}

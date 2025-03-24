package com.palaspadel.sb_palaspadel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UsuUsgId implements java.io.Serializable {
    private static final long serialVersionUID = -370161587941857L;
    @Column(name = "usuusuid", nullable = false)
    private Integer usuusuid;

    @Column(name = "usuusgid", nullable = false)
    private Integer usuusgid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsuUsgId entity = (UsuUsgId) o;
        return Objects.equals(this.usuusuid, entity.usuusuid) &&
                Objects.equals(this.usuusgid, entity.usuusgid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuusuid, usuusgid);
    }

}
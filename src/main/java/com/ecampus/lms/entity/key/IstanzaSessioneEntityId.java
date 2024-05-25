package com.ecampus.lms.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class IstanzaSessioneEntityId implements Serializable {
    private static final long serialVersionUID = 1848689940885508434L;
    @NotNull
    @Column(name = "\"RSEST_FK_DSECR\"", nullable = false)
    private Integer idSessione;

    @NotNull
    @Column(name = "\"RSEST_FK_DUTNE\"", nullable = false)
    private Integer idStudente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IstanzaSessioneEntityId entity = (IstanzaSessioneEntityId) o;
        return Objects.equals(this.idStudente, entity.idStudente) &&
                Objects.equals(this.idSessione, entity.idSessione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudente, idSessione);
    }

}
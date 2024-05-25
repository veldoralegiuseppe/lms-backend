package com.ecampus.lms.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SessioneSummaryEntityId implements Serializable {
    private static final long serialVersionUID = -7033572725398155327L;
    @Column(name = "\"DUTNE_PK_ID\"")
    private Integer idUtente;

    @Column(name = "\"DCORS_PK_ID\"")
    private Integer idCorso;

    @Column(name = "\"DSECR_PK_ID\"")
    private Integer idSessione;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SessioneSummaryEntityId entity = (SessioneSummaryEntityId) o;
        return Objects.equals(this.idCorso, entity.idCorso) &&
                Objects.equals(this.idUtente, entity.idUtente) &&
                Objects.equals(this.idSessione, entity.idSessione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCorso, idUtente, idSessione);
    }

}
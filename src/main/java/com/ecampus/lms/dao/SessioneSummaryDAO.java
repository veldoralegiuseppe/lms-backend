package com.ecampus.lms.dao;

import com.ecampus.lms.entity.SessioneSummaryEntity;
import com.ecampus.lms.entity.key.SessioneSummaryEntityId;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SessioneSummaryDAO extends JpaRepository<SessioneSummaryEntity, SessioneSummaryEntityId> {
    @Query("""
    select s AS SUMMARY, d.email AS EMAIL_DOCENTE, d.nome AS NOME_DOCENTE, d.cognome AS COGNOME_DOCENTE
    from SessioneSummaryEntity s 
    join CorsoEntity c on c.id = s.id.idCorso 
    join c.docente d
    where 
        (:ruolo != 'DOCENTE' or upper(s.email) = :email) AND
        (:ruolo != 'DOCENTE' or :corrette is null or :corrette = true or s.numeroIscritti > 0 and s.proveCorrette < s.proveConsegnate) AND
        (:ruolo != 'DOCENTE' or :corrette is null or :corrette = false or s.numeroIscritti > 0 and s.proveCorrette = s.proveConsegnate) AND
        (:ruolo != 'STUDENTE' or :email = upper(s.email)) AND
        (:nomeCorso is null or upper(s.nomeCorso) = :nomeCorso) AND
        (:tipo is null or upper(s.tipoSessione) = :tipo) AND
        (cast(:dataDa as date) is null or s.dataSessione >= :dataDa) AND 
        (cast(:dataA as date) is null or s.dataSessione <= :dataA )    
    """)
    Page<Tuple> getSummary(@Param("email") String email, @Param("corrette") Boolean corrette, @Param("ruolo") String ruolo, @Param("nomeCorso") String nomeCorso, @Param("tipo") String tipo, @Param("dataDa") LocalDate dataDa, @Param("dataA") LocalDate dataA, Pageable pageable);

    @Query("""
    select vse AS SUMMARY, d.email AS EMAIL_DOCENTE, d.nome AS NOME_DOCENTE, d.cognome AS COGNOME_DOCENTE
    from SessioneSummaryEntity vse join CorsoEntity c on c.id = vse.id.idCorso join c.docente d
    where 
        (vse.ruolo = 'DOCENTE' and vse.dataSessione >= :dataDa) and
        (vse.nomeCorso in (select c.nome from CorsoEntity c join c.studenti s where upper(s.email) = :email)) and
        (vse.nomeCorso not in (select v.nomeCorso from SessioneSummaryEntity v where v.ruolo = 'STUDENTE' and v.dataSessione >= :dataDa and upper(v.email) = :email) ) and
        (:nomeCorso is null or upper(vse.nomeCorso) = :nomeCorso) and
        (:tipo is null or upper(vse.tipoSessione) = :tipo) and
        (cast(:dataA as date) is null or vse.dataSessione <= :dataA ) 
    """)
    Page<Tuple> searchStudente(@Param("email") String email, @Param("nomeCorso") String nomeCorso, @Param("tipo") String tipo, @Param("dataDa") LocalDate dataDa, @Param("dataA") LocalDate dataA, Pageable pageable);

}

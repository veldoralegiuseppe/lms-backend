package com.ecampus.lms.dao;

import com.ecampus.lms.entity.SessioneEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessioneDAO extends JpaRepository<SessioneEntity, Integer> {
    @Query("""
    select distinct
    c.nome as NOME_CORSO, c.id AS ID_CORSO,
    d.id AS ID_DOCENTE, d.nome AS NOME_DOCENTE, d.cognome AS COGNOME_DOCENTE, d.email AS EMAIL_DOCENTE,
    s.nome as NOME_STUDENTE, s.cognome as COGNOME_STUDENTE, s.email as EMAIL_STUDENTE, s.id AS ID_STUDENTE, s.codiceFiscale as CODICE_FISCALE_STUDENTE,
    se.tipo as TIPO_SESSIONE, se.data AS DATA_SESSIONE,
    pr.id as ID_PROVA_SOMMINISTRATA,
    sm.numeroIscritti as NUMERO_ISCRITTI,
    f.id as ID_PROVA_STUDENTE, f.nome as NOME_PROVA_STUDENTE
    from IstanzaSessioneEntity i 
    left join i.provaScritta f
    join i.sessione se
    join SessioneSummaryEntity sm on se.id = sm.id.idSessione
    left join se.provaScritta pr
    join i.studente s 
    join se.corso c 
    join c.docente d
    where se.id = :id
    """)
    List<Tuple> getSessionDetails(@Param("id") Integer id);
}

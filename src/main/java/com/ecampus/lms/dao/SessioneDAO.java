package com.ecampus.lms.dao;

import com.ecampus.lms.entity.SessioneEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SessioneDAO extends JpaRepository<SessioneEntity, Integer> {
    @Query("""
    SELECT se as SESSIONE, c.nome AS NOME_CORSO
    FROM SessioneEntity se
    JOIN se.corso c 
    JOIN c.docente d 
    WHERE d.email = :email
    """)
    Page<Tuple> getSessioniDocente(@Param("email") String email, Pageable pageable);

    @Query("""
    SELECT se as SESSIONE, c.nome AS NOME_CORSO
    FROM SessioneEntity se
    JOIN se.corso c 
    JOIN c.studenti s
    WHERE s.email = :email
    """)
    Page<Tuple> getSessioniStudente(@Param("email") String email, Pageable pageable);

    @Query("""
    SELECT se as SESSIONE, c.nome AS NOME_CORSO
    FROM SessioneEntity se
    JOIN se.corso c 
    """)
    Page<Tuple> getSessioni(Pageable pageable);

    @Query("""
    SELECT c.nome AS NOME_CORSO, se.data AS DATA, se.tipo AS TIPO, d.nome AS NOME_DOCENTE, d.cognome AS COGNOME_DOCENTE, d.email AS EMAIL_DOCENTE, size(s) AS NUMERO_STUDENTI
    FROM SessioneEntity se JOIN se.corso c JOIN c.docente d LEFT JOIN c.studenti s
    WHERE 
        (:nome is null or upper(c.nome) = :nome) AND
        (:tipo is null or upper(se.tipo) = :tipo) AND
        (cast(:dataDa as date) is null or se.data >= :dataDa) AND 
        (cast(:dataA as date) is null or se.data <= :dataA )
    """)
    Page<Tuple> search(@Param("nome") String nomeCorso, @Param("tipo") String tipo, @Param("dataDa") LocalDate dataDa, @Param("dataA") LocalDate dataA, Pageable pageable);
}

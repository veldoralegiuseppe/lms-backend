package com.ecampus.lms.dao;

import com.ecampus.lms.entity.SessioneEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}

package com.ecampus.lms.dao;

import com.ecampus.lms.entity.AttivitaEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttivitaDAO extends JpaRepository<AttivitaEntity, Integer> {

    @Query("""
    SELECT a as ATTIVITA, c.nome as NOME_CORSO
     FROM AttivitaEntity a
     JOIN a.modulo m
     JOIN m.corso c
     JOIN c.studenti s
     WHERE s.ruolo = 'STUDENTE' AND s.email = :email
    """)
    Page<Tuple> findStudenteSummary(@Param("email") String email, Pageable pageable);
}

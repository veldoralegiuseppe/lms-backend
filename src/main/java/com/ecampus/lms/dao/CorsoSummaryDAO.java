package com.ecampus.lms.dao;

import com.ecampus.lms.entity.CorsoSummaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CorsoSummaryDAO extends JpaRepository<CorsoSummaryEntity, String> {
    @Query("""
    SELECT s
    FROM CorsoSummaryEntity s
    WHERE s.nomeCorso IN (
            SELECT c.nome
            FROM CorsoEntity c JOIN c.docente d
            WHERE d.ruolo = 'DOCENTE' AND d.email = :email
        )
    """)
    Page<CorsoSummaryEntity> getDocenteSummary(@Param("email") String email, Pageable pageable);

    @Query("""
    SELECT s
    FROM CorsoSummaryEntity s
    WHERE s.nomeCorso IN (
            SELECT c.nome
            FROM CorsoEntity c JOIN c.studenti s
            WHERE s.ruolo = 'STUDENTE' AND s.email = :email
        )
    """)
    Page<CorsoSummaryEntity> getStudenteSummary(@Param("email") String email, Pageable pageable);

    Page<CorsoSummaryEntity> findAll(Pageable pageable);
}

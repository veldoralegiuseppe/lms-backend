package com.ecampus.lms.dao;

import com.ecampus.lms.entity.CorsoEntity;
import com.ecampus.lms.enums.UserRole;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CorsoDAO extends JpaRepository<CorsoEntity, Integer> {
    @Query(value = """
    SELECT\s
    c.nome as NOME_CORSO,
    (
        SELECT COUNT(cr)
    	FROM CorsoEntity cr JOIN cr.sessioni
    	GROUP BY cr.nome
    ) AS NUMERO_SESSIONI,
    (
        SELECT COUNT(cr)
    	FROM CorsoEntity cr JOIN cr.moduli
    	GROUP BY cr.nome
    ) AS NUMERO_MODULI,
    (
        SELECT COUNT(cr)
    	FROM CorsoEntity cr JOIN cr.studenti
    	GROUP BY cr.nome
    ) AS NUMERO_STUDENTI
    FROM CorsoEntity c
    WHERE c.id IN (
        CASE
            WHEN :ruolo = 'STUDENTE' THEN (
                    SELECT c.id
                    FROM CorsoEntity c JOIN c.studenti s
                    WHERE s.id = (
                        SELECT u.id
                        FROM UtenteEntity u
                        WHERE u.ruolo = :ruolo AND u.email = :email
                    )
            )
            WHEN :ruolo = 'DOCENTE' THEN (
                SELECT cr.id
                FROM CorsoEntity cr
                WHERE cr.fkProfessore = (
                    SELECT u.id
                    FROM UtenteEntity u
                    WHERE u.ruolo = :ruolo AND u.email = :email
                )
            )
            WHEN :ruolo = 'ADMIN' THEN (
                SELECT cr.id
                FROM CorsoEntity cr
            )
            ELSE 0
        END
     )
     GROUP BY c.nome         
    """, nativeQuery = false)
    Page<Tuple> findSummary(@Param("ruolo") UserRole role, @Param("email") String email, Pageable pageable);

}

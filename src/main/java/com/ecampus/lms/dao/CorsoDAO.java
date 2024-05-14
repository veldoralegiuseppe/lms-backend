package com.ecampus.lms.dao;

import com.ecampus.lms.entity.CorsoEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CorsoDAO extends JpaRepository<CorsoEntity, Integer> {
    @Query(value = """
    SELECT
    "DCORS_NOME" AS "NOME_CORSO",
    (
        SELECT COUNT(*)
        FROM "DCORS_CORSO"
        JOIN "DSECR_SESSIONE" ON "DSECR_FK_DCORS" = "DCORS_PK_ID"
        GROUP BY "DCORS_NOME"
    ) AS "NUMERO_SESSIONI",
    (
        SELECT COUNT(*)
        FROM "DCORS_CORSO"
        JOIN "DMODL_MODULO" ON "DMODL_FK_DCORS" = "DCORS_PK_ID"
        GROUP BY "DCORS_NOME"
    ) AS "NUMERO_MODULI",
    (
        SELECT COUNT(*)
        FROM "DCORS_CORSO"
        JOIN "RCRST_CORSO_STUDENTE" ON "RCRST_FK_DCORS" = "DCORS_PK_ID"
        GROUP BY "DCORS_NOME"
    ) AS "NUMERO_STUDENTI"
    FROM "DCORS_CORSO"
    WHERE "DCORS_PK_ID" IN (
        SELECT "DCORS_PK_ID"
        FROM "DCORS_CORSO" JOIN "RCRST_CORSO_STUDENTE" ON "RCRST_FK_DCORS" = "DCORS_PK_ID"
        WHERE "RCRST_FK_DUTNE" = :idStudente
    )
    GROUP BY "DCORS_NOME"
    """, nativeQuery = true)
    List<Tuple> findStudenteSummary(@Param("idStudente") Integer idStudente);

}

package com.ecampus.lms.dao;

import com.ecampus.lms.beans.DettaglioCorsoBean;
import com.ecampus.lms.entity.CorsoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CorsoDAO extends JpaRepository<CorsoEntity, Integer> {
    @Query("""
    SELECT c.nome
    FROM CorsoEntity c
    """)
    List<String> getAllNomeCorsi();

    Optional<CorsoEntity> findByNomeIgnoreCase(@NonNull String nome);

    @Query("""
    SELECT c.nome
    FROM CorsoEntity c
    WHERE c.docente.email = :email
    """)
    List<String> getAllNomeCorsiByDocente(@NonNull @Param("email") String email);

    List<CorsoEntity> findByDocenteNull();

    List<CorsoEntity> findByStudenti_EmailIgnoreCase(String email);

    @Query("""
    select 
    new com.ecampus.lms.beans.DettaglioCorsoBean(c.id, m.id, a.id , c.nome, c.descrizione, m.nome, m.descrizione, a.tipo, a.settimanaProgrammata, f.id, f.nome, f.tipo, f.insertDate, f.updateDate)
    from CorsoEntity c left join c.moduli m left join m.attivita a left join a.file f
    where c.id = :idCorso
    """)
    List<DettaglioCorsoBean> findDettaglio(@Param("idCorso") Integer idCorso);

}

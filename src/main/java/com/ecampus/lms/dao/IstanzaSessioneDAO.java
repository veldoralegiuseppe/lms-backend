package com.ecampus.lms.dao;

import com.ecampus.lms.entity.DocumentaleEntity;
import com.ecampus.lms.entity.IstanzaSessioneEntity;
import com.ecampus.lms.entity.key.IstanzaSessioneEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

public interface IstanzaSessioneDAO extends JpaRepository<IstanzaSessioneEntity, IstanzaSessioneEntityId> {
    @Transactional
    @Modifying
    @Query("update IstanzaSessioneEntity i set i.esito = :esito where i.id in :ids")
    void updateEsito(@Param("esito") String esito, @Param("ids") Collection<IstanzaSessioneEntityId> ids);

    @Query("select i from IstanzaSessioneEntity i where i.id.idSessione = ?1 and i.studente.email = ?2")
    Optional<IstanzaSessioneEntity> findIstanzaSessione(Integer idSessione, String email);

    @Transactional
    @Modifying
    @Query("update IstanzaSessioneEntity i set i.provaScritta = ?1 where i.id = ?2")
    void updateProvaScritta(DocumentaleEntity provaScritta, IstanzaSessioneEntityId id);

}

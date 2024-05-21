package com.ecampus.lms.dao;

import com.ecampus.lms.entity.UtenteEntity;
import com.ecampus.lms.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface UtenteDAO extends JpaRepository<UtenteEntity, Integer> {

    @Query("select u from UtenteEntity u where u.ruolo = ?1")
    Page<UtenteEntity> findByRole(@NonNull UserRole ruolo, Pageable pageable);

    boolean existsByCodiceFiscaleIgnoreCase(@NonNull String codiceFiscale);

    boolean existsByEmailIgnoreCase(@NonNull String email);

    @Query("select u from UtenteEntity u where upper(u.email) = upper(?1)")
    Optional<UtenteEntity> findByEmail(@NonNull String email);

    @Query("""
            select distinct u
            from 
            UtenteEntity u left join u.corsi cr left join CorsoEntity c on (c.docente.id = u.id or c.id = cr.id)
            where 
                    (:nome is null or upper(u.nome) = :nome) and
                    (:cognome is null or upper(u.cognome) = :cognome) and
                    (:email is null or upper(u.email) = :email) and
                    (:ruolo is null or u.ruolo = :ruolo) and
                    (:cf is null or upper(u.codiceFiscale) = :cf) and 
                    (:nomeCorso is null or upper(c.nome) = :nomeCorso)
            """)
    Page<UtenteEntity> searchBy(@Nullable @Param("nome") String nome,
                                @Nullable @Param("cognome") String cognome,
                                @Nullable @Param("email") String email,
                                @Nullable @Param("ruolo") UserRole ruolo,
                                @Nullable @Param("cf") String codiceFiscale,
                                @Nullable @Param("nomeCorso") String nomeCorso,
                                Pageable pageable);

    List<UtenteEntity> findByRuolo(UserRole ruolo);
}

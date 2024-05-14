package com.ecampus.lms.dao;

import com.ecampus.lms.entity.UtenteEntity;
import com.ecampus.lms.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UtenteDAO extends JpaRepository<UtenteEntity, Integer> {

    @Query("select u from UtenteEntity u where u.ruolo = ?1")
    Page<UtenteEntity> findByRole(@NonNull UserRole ruolo, Pageable pageable);

    boolean existsByCodiceFiscale(@NonNull String codiceFiscale);

    boolean existsByEmailIgnoreCase(@NonNull String email);

    @Query("select u from UtenteEntity u where upper(u.email) = upper(?1)")
    Optional<UtenteEntity> findByEmail(@NonNull String email);
}

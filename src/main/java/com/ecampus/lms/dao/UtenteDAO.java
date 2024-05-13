package com.ecampus.lms.dao;

import com.ecampus.lms.entity.Utente;
import com.ecampus.lms.enums.UserRole;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UtenteDAO extends JpaRepository<Utente, Integer> {
    @Query("select u from Utente u where u.ruolo = ?1")
    Page<Utente> findByRole(@NonNull UserRole ruolo, Pageable pageable);

    boolean existsByCodiceFiscale(@NonNull String codiceFiscale);

    @Query("select u from Utente u where upper(u.email) = upper(?1)")
    Optional<Utente> findByEmail(@NonNull String email);
}

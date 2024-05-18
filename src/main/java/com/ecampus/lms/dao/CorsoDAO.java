package com.ecampus.lms.dao;

import com.ecampus.lms.entity.CorsoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


}

package com.ecampus.lms.dao;

import com.ecampus.lms.entity.ModuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuloDAO extends JpaRepository<ModuloEntity, Integer> {
    List<ModuloEntity> findByCorso_Id(Integer id);
}

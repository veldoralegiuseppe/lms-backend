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
}

package com.ecampus.lms.dao;

import com.ecampus.lms.entity.SessioneEntity;
import com.ecampus.lms.entity.SessioneSummaryEntity;
import com.ecampus.lms.entity.UtenteEntity;
import com.ecampus.lms.enums.UserRole;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

public interface SessioneDAO extends JpaRepository<SessioneEntity, Integer> {

}

package com.ecampus.lms.dao;

import com.ecampus.lms.entity.IstanzaSessioneEntity;
import com.ecampus.lms.entity.key.IstanzaSessioneEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IstanzaSessioneDAO extends JpaRepository<IstanzaSessioneEntity, IstanzaSessioneEntityId> {
}

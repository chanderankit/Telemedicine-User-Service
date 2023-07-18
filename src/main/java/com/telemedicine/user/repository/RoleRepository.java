package com.telemedicine.user.repository;

import com.telemedicine.user.model.dao.RoleDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleDao, Integer> {
}

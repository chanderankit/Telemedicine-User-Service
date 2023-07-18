package com.telemedicine.user.repository;

import com.telemedicine.user.model.dao.ClinicDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClinicRepository extends JpaRepository<ClinicDao, UUID> {
}

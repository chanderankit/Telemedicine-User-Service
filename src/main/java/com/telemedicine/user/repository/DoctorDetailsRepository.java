package com.telemedicine.user.repository;

import com.telemedicine.user.model.dao.DoctorDetailsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorDetailsRepository extends JpaRepository<DoctorDetailsDao, UUID> {
    Optional<DoctorDetailsDao> findByUserId(UUID id);
}

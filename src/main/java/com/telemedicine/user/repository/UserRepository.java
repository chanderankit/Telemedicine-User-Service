package com.telemedicine.user.repository;

import com.telemedicine.user.model.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDao, UUID> {
    Optional<UserDao> findByEmail(String id);
}

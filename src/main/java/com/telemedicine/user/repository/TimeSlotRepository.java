package com.telemedicine.user.repository;

import com.telemedicine.user.model.dao.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
}

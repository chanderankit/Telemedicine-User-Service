package com.telemedicine.user.repository;

import com.telemedicine.user.model.dao.DoctorAvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoctorSlotRepository extends JpaRepository<DoctorAvailabilitySlot, Long> {
    List<DoctorAvailabilitySlot> findByDoctorId(UUID doctorId);
    List<DoctorAvailabilitySlot> findByDoctorIdAndWeekdayAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            UUID doctorId, String weekday, String endTime, String startTime);
}

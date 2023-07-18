package com.telemedicine.user.service;

import com.telemedicine.user.model.dao.DoctorAvailabilitySlot;
import com.telemedicine.user.model.dto.DoctorAvailabilitySlotDto;
import com.telemedicine.user.model.dto.DoctorSlot;

import java.util.List;
import java.util.UUID;

public interface DoctorAvailabilityService {
    List<DoctorSlot> createDoctorAvailability(DoctorSlot doctorSlot);
    DoctorAvailabilitySlot getSlotById(long id);
    List<DoctorAvailabilitySlotDto> getDoctorSlotsByDoctorId(UUID doctorId);
}

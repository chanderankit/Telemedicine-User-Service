package com.telemedicine.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.user.enums.ErrorCodes;
import com.telemedicine.user.enums.WeekDays;
import com.telemedicine.user.exception.BusinessException;
import com.telemedicine.user.model.dao.ClinicDao;
import com.telemedicine.user.model.dao.DoctorAvailabilitySlot;
import com.telemedicine.user.model.dao.DoctorDetailsDao;
import com.telemedicine.user.model.dto.DoctorAvailabilitySlotDto;
import com.telemedicine.user.model.dto.DoctorSlot;
import com.telemedicine.user.repository.DoctorSlotRepository;
import com.telemedicine.user.service.ClinicService;
import com.telemedicine.user.service.DoctorAvailabilityService;
import com.telemedicine.user.service.DoctorDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {
    private final DoctorSlotRepository slotRepository;
    private final ClinicService clinicService;
    private final DoctorDetailsService doctorService;
    private final ObjectMapper objectMapper;
    @Autowired
    public DoctorAvailabilityServiceImpl(DoctorSlotRepository slotRepository, ClinicService clinicService, DoctorDetailsService doctorService, ObjectMapper objectMapper) {
        this.slotRepository = slotRepository;
        this.clinicService = clinicService;
        this.doctorService = doctorService;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<DoctorSlot> createDoctorSlot(DoctorSlot doctorSlot) {
        try {
            LocalTime startTime = LocalTime.parse(doctorSlot.getStartTime());
            LocalTime endTime = LocalTime.parse(doctorSlot.getEndTime());

            if(!startTime.isBefore(endTime)) throw new BusinessException(ErrorCodes.TIME_ERROR, HttpStatus.BAD_REQUEST);

        } catch (DateTimeException ex) {
            log.error("--------DoctorAvailabilityServiceImpl:createDoctorAvailability::---{}----", ex.getMessage());
            throw new BusinessException(ErrorCodes.INVALID_TIME, HttpStatus.BAD_REQUEST);
        }

        DoctorDetailsDao doctor = doctorService.getDoctorById(doctorSlot.getDoctorId());
        ClinicDao clinic = clinicService.getClinicById(doctorSlot.getClinicId());

        List<DoctorSlot> slots = new ArrayList<>();
        for (WeekDays day : doctorSlot.getWeekDays()) {
            List<DoctorAvailabilitySlot> overlappingSlots = slotRepository.findByDoctorIdAndWeekdayAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(doctorSlot.getDoctorId(), day.getValue(), doctorSlot.getStartTime(), doctorSlot.getEndTime());
            if (!overlappingSlots.isEmpty()) {
                throw new BusinessException(ErrorCodes.SLOT_TIMING_CONFLICT, HttpStatus.BAD_REQUEST);
            }

            DoctorAvailabilitySlot slot = new DoctorAvailabilitySlot();
            slot.setDoctor(doctor);
            slot.setClinic(clinic);
            slot.setStartTime(doctorSlot.getStartTime());
            slot.setEndTime(doctorSlot.getEndTime());
            slot.setWeekday(day.getValue());
            slot = slotRepository.save(slot);

            log.info("---DoctorAvailabilityServiceImpl:createDoctorAvailability::--new doctor slot is created--> {}---", slot.getId());
            DoctorSlot newSlot = new DoctorSlot();
            newSlot.setId(slot.getId());
            newSlot.setDoctorId(doctorSlot.getDoctorId());
            newSlot.setClinicId(doctorSlot.getClinicId());
            newSlot.setStartTime(doctorSlot.getStartTime());
            newSlot.setEndTime(doctorSlot.getEndTime());
            newSlot.setWeekDays(Collections.singletonList(day));
            slots.add(newSlot);
        }
        return slots;
    }

    @Override
    public DoctorAvailabilitySlot getSlotById(long id) {
        Optional<DoctorAvailabilitySlot> doctorSlot = slotRepository.findById(id);
        if(!doctorSlot.isPresent()) {
            log.info("-----DoctorAvailabilityServiceImpl:getDoctorSlotById::--doctor not found with id {}---", id);
            throw new BusinessException(ErrorCodes.SLOT_NOT_PRESENT, HttpStatus.BAD_REQUEST);
        }
        return doctorSlot.get();
    }

    @Override
    public List<DoctorAvailabilitySlotDto> getDoctorSlotsByDoctorId(UUID doctorId) {
        List<DoctorAvailabilitySlot> slots = slotRepository.findByDoctorId(doctorId);
        return slots.stream().map(slot -> objectMapper.convertValue(slot, DoctorAvailabilitySlotDto.class)).collect(Collectors.toList());
    }
}

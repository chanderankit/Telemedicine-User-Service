package com.telemedicine.user.controller;

import com.telemedicine.user.model.dao.DoctorAvailabilitySlot;
import com.telemedicine.user.model.dto.DoctorAvailabilitySlotDto;
import com.telemedicine.user.model.dto.DoctorSlot;
import com.telemedicine.user.service.DoctorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/doctors-slots")
public class DoctorAvailabilityController implements Serializable {
    private final DoctorAvailabilityService doctorAvailabilityService;
    @Autowired
    public DoctorAvailabilityController(DoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }

    @PostMapping
    public ResponseEntity<List<DoctorSlot>> addDoctorSlot(@Valid @RequestBody DoctorSlot slot) {
        List<DoctorSlot> doctorSlot = doctorAvailabilityService.createDoctorAvailability(slot);
        return new ResponseEntity<>(doctorSlot, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorAvailabilitySlot> getSlotById(@PathVariable("id") long id) {
        DoctorAvailabilitySlot doctorAvailabilitySlot = doctorAvailabilityService.getSlotById(id);
        return new ResponseEntity<>(doctorAvailabilitySlot, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DoctorAvailabilitySlotDto>> getDoctorSlotsByDoctorId(@RequestParam("doctorId") UUID doctorId) {
        List<DoctorAvailabilitySlotDto> doctorAvailabilitySlot = doctorAvailabilityService.getDoctorSlotsByDoctorId(doctorId);
        return new ResponseEntity<>(doctorAvailabilitySlot, HttpStatus.OK);
    }
}

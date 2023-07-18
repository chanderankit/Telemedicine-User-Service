package com.telemedicine.user.controller;

import com.telemedicine.user.model.dao.ClinicDao;
import com.telemedicine.user.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/clinics")
public class ClinicController {
    private final ClinicService clinicService;
    @Autowired
    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping
    public ResponseEntity<ClinicDao> addClinic(@RequestBody ClinicDao clinic) {
        ClinicDao savedClinic = clinicService.addClinic(clinic);
        return new ResponseEntity<>(savedClinic, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicDao> getClinicById(@PathVariable("id") UUID id) {
        ClinicDao clinic = clinicService.getClinicById(id);
        return new ResponseEntity<>(clinic, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClinicDao>> getAllClinics() {
        List<ClinicDao> clinics = clinicService.getAllClinic();
        return new ResponseEntity<>(clinics, HttpStatus.OK);
    }
}

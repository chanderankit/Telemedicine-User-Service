package com.telemedicine.user.controller;

import com.telemedicine.user.model.dao.DoctorDetailsDao;
import com.telemedicine.user.service.DoctorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/doctors")
public class DoctorController {
    @Autowired
    private DoctorDetailsService doctorDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailsDao> getDoctorById(@PathVariable("id") UUID id) {
        DoctorDetailsDao doctorFromDb = doctorDetailsService.getDoctorById(id);
        return new ResponseEntity<>(doctorFromDb, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDetailsDao>> getAllDoctorDetails() {
        List<DoctorDetailsDao> doctorsFromDb = doctorDetailsService.getAllDoctorsDetails();
        return new ResponseEntity<>(doctorsFromDb, HttpStatus.OK);
    }
}

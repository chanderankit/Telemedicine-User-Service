package com.telemedicine.user.service;


import com.telemedicine.user.model.dao.DoctorDetailsDao;

import java.util.List;
import java.util.UUID;

public interface DoctorDetailsService {
    DoctorDetailsDao getDoctorById(UUID id);
    DoctorDetailsDao getDoctorByUserId(UUID id);
    List<DoctorDetailsDao> getAllDoctorsDetails();
}

package com.telemedicine.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.user.enums.ErrorCodes;
import com.telemedicine.user.exception.BusinessException;
import com.telemedicine.user.model.dao.DoctorDetailsDao;
import com.telemedicine.user.repository.DoctorDetailsRepository;
import com.telemedicine.user.service.DoctorDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class DoctorDetailsServiceImpl implements DoctorDetailsService {

    private final DoctorDetailsRepository doctorDetailsRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public DoctorDetailsServiceImpl(final DoctorDetailsRepository doctorDetailsRepository,
                                    final ObjectMapper objectMapper){

        this.doctorDetailsRepository = doctorDetailsRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public DoctorDetailsDao getDoctorById(UUID id) {
        Optional<DoctorDetailsDao> doctorFromDb = doctorDetailsRepository.findById(id);

        if(!doctorFromDb.isPresent()){
            log.error("-------------DoctorDetailsServiceImpl:findDoctorByUserId::Doctor not found found with user id {}-----------",id);
            throw new BusinessException(ErrorCodes.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        return doctorFromDb.get();
    }

    @Override
    public DoctorDetailsDao getDoctorByUserId(UUID id) {
        Optional<DoctorDetailsDao> doctorFromDb = doctorDetailsRepository.findByUserId(id);

        if(!doctorFromDb.isPresent()){
            log.error("-------------DoctorDetailsServiceImpl:findDoctorByUserId::Doctor not found found with user id {}-----------",id);
            throw new BusinessException(ErrorCodes.DOCTOR_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        return doctorFromDb.get();
    }

    @Override
    public List<DoctorDetailsDao> getAllDoctorsDetails() {
        List<DoctorDetailsDao> doctorDetailsList = doctorDetailsRepository.findAll();
        return doctorDetailsList;
    }
}

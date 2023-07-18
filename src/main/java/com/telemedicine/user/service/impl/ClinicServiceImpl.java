package com.telemedicine.user.service.impl;

import com.telemedicine.user.enums.ErrorCodes;
import com.telemedicine.user.exception.BusinessException;
import com.telemedicine.user.model.dao.ClinicDao;
import com.telemedicine.user.repository.ClinicRepository;
import com.telemedicine.user.service.ClinicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ClinicServiceImpl implements ClinicService {
    private final ClinicRepository clinicRepository;
    @Autowired
    public ClinicServiceImpl(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public ClinicDao addClinic(ClinicDao clinic) {
        clinic = clinicRepository.save(clinic);
        return clinic;
    }

    @Override
    public List<ClinicDao> getAllClinic() {
        return clinicRepository.findAll();
    }

    @Override
    public ClinicDao getClinicById(UUID id) {
        Optional<ClinicDao> clinic = clinicRepository.findById(id);
        if(!clinic.isPresent()) {
            log.info("---------ClinicServiceImpl:getClinicById::  no clinic found for id: {}-", id);
            throw new BusinessException(ErrorCodes.CLINIC_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        return clinic.get();
    }
}

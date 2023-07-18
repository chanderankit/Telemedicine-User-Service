package com.telemedicine.user.service;

import com.telemedicine.user.model.dao.ClinicDao;

import java.util.List;
import java.util.UUID;

public interface ClinicService {
    ClinicDao addClinic(ClinicDao clinic);
    List<ClinicDao> getAllClinic();
    ClinicDao getClinicById(UUID id);
}

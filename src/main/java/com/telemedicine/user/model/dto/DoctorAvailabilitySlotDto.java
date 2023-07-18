package com.telemedicine.user.model.dto;

import com.telemedicine.user.model.dao.BaseDao;
import com.telemedicine.user.model.dao.ClinicDao;
import lombok.Data;

@Data
public class DoctorAvailabilitySlotDto extends BaseDao {
    private long id;
    private ClinicDao clinic;
    private String weekday;
    private String startTime;
    private String endTime;
}

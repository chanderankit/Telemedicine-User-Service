package com.telemedicine.user.model.dto;

import com.telemedicine.user.enums.WeekDays;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class DoctorSlot {
    private long id;
    @NotNull(message = "doctor-id is required")
    private UUID doctorId;
    @NotNull(message = "clinic-id is required")
    private UUID clinicId;
    private List<WeekDays> weekDays;
    @NotNull(message = "startTime is required")
    private String startTime;
    @NotNull(message = "endTime is required")
    private String endTime;
}

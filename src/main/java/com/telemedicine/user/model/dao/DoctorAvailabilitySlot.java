package com.telemedicine.user.model.dao;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class DoctorAvailabilitySlot extends BaseDao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorDetailsDao doctor;
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private ClinicDao clinic;
    private String weekday;
    private String startTime;
    private String endTime;
}

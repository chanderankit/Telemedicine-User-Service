package com.telemedicine.user.model.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "time_slots")
@Data
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String time;
}

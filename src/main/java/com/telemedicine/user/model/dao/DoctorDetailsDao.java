package com.telemedicine.user.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "doctorsDetails")
public class DoctorDetailsDao extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private UserDao user;
    private Boolean availabilityStatus;
    private int averageConsultationTime;
    private String fee;
    private Boolean videoMode;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DoctorAvailabilitySlot> doctorSlots = new ArrayList<>();
}

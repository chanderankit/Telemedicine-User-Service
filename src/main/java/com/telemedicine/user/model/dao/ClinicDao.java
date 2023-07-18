package com.telemedicine.user.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clinics")
public class ClinicDao extends BaseDao{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "address_id")
    private AddressDao address;
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<DoctorAvailabilitySlot> doctorSlots = new HashSet<>();
}

package com.telemedicine.user.model.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "address")
public class AddressDao extends BaseDao{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "area")
    private String area;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "latitude")
    private double latitude;
}

package com.telemedicine.user.model.dao;

import com.telemedicine.user.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class UserDao extends BaseDao{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @OneToOne
    @JoinColumn(name = "role_id")
    private RoleDao position;
    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private AddressDao address;
    @Column(name = "otp_verified")
    private boolean isOtpVerified;
}

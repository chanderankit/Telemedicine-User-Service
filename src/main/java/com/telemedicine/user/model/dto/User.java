package com.telemedicine.user.model.dto;

import com.telemedicine.user.enums.Gender;
import com.telemedicine.user.enums.Role;
import com.telemedicine.user.model.dao.AddressDao;
import com.telemedicine.user.model.dao.DoctorDetailsDao;
import com.telemedicine.user.util.Constants;
import lombok.Data;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class User {
    private UUID id;
    @NotNull(message = "first name should not be null")
    @Pattern(regexp = "[a-zA-Z]+",message = "First name can only contain alphabetical characters")
    private String firstName;
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "Last name can only contain alphabetical characters")
    private String lastName;
    private String countryCode = "+91";
    @NotNull(message = "mobile number is mandatory")
    @Pattern(regexp = "\\d+", message = "Please enter valid mobile number")
    @Size(min = 6, max = 16, message = "Mobile number must be between {min} and {max} digits")
    private String mobileNumber;
    @NotNull(message = "Please specify gender")
    private Gender gender;
    @NotNull(message = "email is missing ")
    @Email(message = "please enter valid email")
    private String email;
    @NotNull(message = "date of birth is missing")
    private String dateOfBirth;
    private String role = Constants.PATIENT;
    @Nullable
    private String profilePhotoUrl;
    @NotNull(message = "address is required", groups = PatientValidation.class)
    private AddressDao address;
    private boolean isOtpVerified;
    @NotNull(message = "please enter doctor details",groups = {DoctorValidation.class})
    private DoctorDetailsDao doctorDetails;
    public interface DoctorValidation {
    }
    public interface PatientValidation {
    }
}

package com.telemedicine.user.controller;

import com.telemedicine.user.enums.ErrorCodes;
import com.telemedicine.user.enums.Role;
import com.telemedicine.user.exception.BusinessException;
import com.telemedicine.user.model.dto.User;
import com.telemedicine.user.service.OtpService;
import com.telemedicine.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final OtpService otpService;
    private final Validator validator;

    @Autowired
    public UserController(final UserService userService, OtpService otpService, final Validator validator){
        this.userService = userService;
        this.otpService = otpService;
        this.validator = validator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody User user){
        Set<ConstraintViolation<Object>> violations;
        if(user.getRole().equals(Role.DOCTOR)) violations = validator.validate(user, User.DoctorValidation.class);
        else violations = validator.validate(user, User.PatientValidation.class);

        if (!violations.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(violations.stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage)));

        user = userService.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody String email) {
        if(Strings.isEmpty(email)) throw new BusinessException(HttpStatus.BAD_REQUEST);
        otpService.sendOtp(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") UUID id) {
        User response = userService.findUserById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") UUID id,@RequestBody User updatedUser){
        if (!Objects.isNull(updatedUser.getEmail())) {
            throw new BusinessException(ErrorCodes.EMAIL_CANNOT_BE_CHANGED,HttpStatus.BAD_REQUEST);
        }
        User response = userService.updateUser(id,updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

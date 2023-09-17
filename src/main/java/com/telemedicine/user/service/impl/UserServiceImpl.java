package com.telemedicine.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.user.enums.ErrorCodes;
import com.telemedicine.user.exception.BusinessException;
import com.telemedicine.user.model.dao.DoctorDetailsDao;
import com.telemedicine.user.model.dao.RoleDao;
import com.telemedicine.user.model.dao.UserDao;
import com.telemedicine.user.model.dto.User;
import com.telemedicine.user.model.payload.request.OtpRequest;
import com.telemedicine.user.repository.DoctorDetailsRepository;
import com.telemedicine.user.repository.RoleRepository;
import com.telemedicine.user.repository.UserRepository;
import com.telemedicine.user.service.OtpService;
import com.telemedicine.user.service.UserService;
import com.telemedicine.user.util.CommonUtils;
import com.telemedicine.user.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;
    private final OtpService otpService;
    private final DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           RoleRepository roleRepository, final OtpService otpService,
                           final ObjectMapper objectMapper,
                           DoctorDetailsRepository doctorDetailsRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;
        this.otpService = otpService;
        this.doctorDetailsRepository = doctorDetailsRepository;
    }

    @Override
    public User registerUser(User user) {
        if(Objects.isNull(user)){
            log.error("--------------UserServiceImpl:registerUser:: error -> request is null--------");
            throw new BusinessException(HttpStatus.BAD_REQUEST);
        }

        if(emailExists(user.getEmail())){
            log.error("--------------UserServiceImpl:registerUser:: error -> email already exists----------");
            throw new BusinessException(ErrorCodes.EMAIL_ALREADY_EXISTS,HttpStatus.BAD_REQUEST);
        }

        log.info("------------------UserServiceImpl:registerUser:: creating new user with email -> {}",user.getEmail());

        UserDao userDb = objectMapper.convertValue(user,UserDao.class);
        Optional<RoleDao> role;
        if(user.getRole().equals(Constants.PATIENT)) role = roleRepository.findById(1);
        else role = roleRepository.findById(2);

        userDb.setPosition(role.get());
        userDb = userRepository.save(userDb);

        if(role.get().getName().equals(Constants.DOCTOR)){
            DoctorDetailsDao doctorDetails = user.getDoctorDetails();
            doctorDetails.setUser(userDb);
            doctorDetails = doctorDetailsRepository.save(doctorDetails);

            user = objectMapper.convertValue(userDb,User.class);
            user.setDoctorDetails(doctorDetails);
        } else {
            user = objectMapper.convertValue(userDb,User.class);
        }


//        sending otp mail to user
        otpService.sendOtp(OtpRequest.builder()
                .email(user.getEmail())
                .userName(userDb.getFirstName().concat(userDb.getLastName()))
                .build());

        return user;
    }

    @Override
    public User findUserById(UUID id) {
        Optional<UserDao> userFromDb = userRepository.findById(id);

        if(!userFromDb.isPresent()){
            log.error("-------------UserServiceImpl:findUserById::no used with id : {}-----------",id);
            throw new BusinessException(ErrorCodes.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        UserDao userDb = userFromDb.get();
        return objectMapper.convertValue(userDb, User.class);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<UserDao> userFromDb = userRepository.findByEmail(email);

        if(!userFromDb.isPresent()){
            log.error("-------------UserServiceImpl:findUserByEmail::no used with emailId : {}-----------",email);
            throw new BusinessException(ErrorCodes.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        UserDao user = userFromDb.get();
        if(!user.isOtpVerified())
            throw new BusinessException(ErrorCodes.USER_NOT_VERIFIED, HttpStatus.BAD_REQUEST);

//        if(user.getRole())

        return objectMapper.convertValue(user, User.class);
    }

    @Override
    public User updateUser(UUID id, User updatedUser) {

        UserDao userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND,HttpStatus.NOT_FOUND));

        UserDao updatedResource = objectMapper.convertValue(updatedUser, UserDao.class);
        BeanUtils.copyProperties(updatedResource,userFromDb, CommonUtils.getNullPropertyNames(updatedResource));

        userFromDb = userRepository.save(userFromDb);
        return objectMapper.convertValue(userFromDb, User.class);

    }

    @Override
    public User findUserByEmail(String email) {
        Optional<UserDao> userFromDb = userRepository.findByEmail(email);

        if(!userFromDb.isPresent()){
            log.error("-------------UserServiceImpl:findUserByEmail::no used with emailId : {}-----------",email);
            throw new BusinessException(ErrorCodes.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        UserDao userDb = userFromDb.get();
        String userRole = userDb.getPosition().getName();

        User user = objectMapper.convertValue(userDb, User.class);
        if(userRole.equals("Role_DOCTOR")) user.setRole(Constants.DOCTOR);
        else user.setRole(Constants.PATIENT);

        return user;
    }

    @Override
    public void updateUserVerification(UUID id) {
        Optional<UserDao> userFromDb = userRepository.findById(id);
        if(userFromDb.isPresent()) {
            userFromDb.get().setOtpVerified(true);
            userRepository.save(userFromDb.get());
        }
    }

    private boolean emailExists(String email) {
        Optional<UserDao> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

}

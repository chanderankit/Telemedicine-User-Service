package com.telemedicine.user.service.impl;

import com.telemedicine.user.retrofit.authentication.AuthGateway;
import com.telemedicine.user.enums.ErrorCodes;
import com.telemedicine.user.enums.NotificationType;
import com.telemedicine.user.enums.Role;
import com.telemedicine.user.enums.Status;
import com.telemedicine.user.exception.BusinessException;
import com.telemedicine.user.model.payload.Token;
import com.telemedicine.user.model.dao.DoctorDetailsDao;
import com.telemedicine.user.model.dto.User;
import com.telemedicine.user.model.payload.request.NotificationRequest;
import com.telemedicine.user.model.payload.request.OtpRequest;
import com.telemedicine.user.model.payload.response.OtpResponse;
import com.telemedicine.user.retrofit.notifications.NotificationGateway;
import com.telemedicine.user.service.DoctorDetailsService;
import com.telemedicine.user.service.OtpService;
import com.telemedicine.user.service.UserService;
import com.telemedicine.user.util.Constants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@EnableAsync
public class OtpServiceImpl implements OtpService {

    @Value("${redis.time-to-live}")
    private long OTP_TTL;

    private final RedisTemplate<String, String> redisTemplate;
    private final NotificationGateway notificationGateway;
    private final AuthGateway authGateway;
    private final DoctorDetailsService doctorDetailsService;
    private final UserService userService;
    @Autowired
    public OtpServiceImpl(final RedisTemplate<String,String> redisTemplate,
                          final NotificationGateway notificationGateway,
                          final AuthGateway authGateway,
                          final DoctorDetailsService doctorDetailsService,
                          final @Lazy UserService userService){
        this.redisTemplate = redisTemplate;
        this.notificationGateway = notificationGateway;
        this.userService = userService;
        this.doctorDetailsService = doctorDetailsService;
        this.authGateway = authGateway;
    }

//    @Async
    @Override
    public void sendOtp(OtpRequest request) {

        log.info("------------------OtpServiceImpl:sendOtp:: sending verify otp to -> {}---------",request.getEmail());

        Map<String,String> requestModel = new HashMap<>();
        requestModel.put("name",request.getUserName());
        requestModel.put("otp",generateOtp(request.getEmail()));

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .to(request.getEmail())
                .subject(Constants.SUBJECT_VERIFY_OTP)
                .templateType(Constants.OTP_VERIFICATION)
                .model(requestModel)
                .build();

        //send email to user through notification service
        try {
            notificationGateway.sendNotification(notificationRequest, NotificationType.EMAIL.getValue());
        } catch (IOException e) {
            log.error("-------RegisterServiceImpl:sendOtp error message -> {}",e.getMessage());
            throw new BusinessException(ErrorCodes.CONNECTION_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

    @Async
    @Override
    public void sendOtp(String email) {
        log.info("------------------OtpServiceImpl:sendOtp:: sending verify otp to -> {}---------", email);

        Map<String,String> requestModel = new HashMap<>();
        requestModel.put("otp",generateOtp(email));

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .to(email)
                .subject(Constants.SUBJECT_VERIFY_OTP)
                .templateType(Constants.LOGIN)
                .model(requestModel)
                .build();

        //send email to user through notification service
        try {
            notificationGateway.sendNotification(notificationRequest, NotificationType.EMAIL.getValue());
        } catch (IOException e) {
            log.error("-------RegisterServiceImpl:sendOtp error message -> {}",e.getMessage());
            throw new BusinessException(ErrorCodes.CONNECTION_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    public OtpResponse verifyOtp(OtpRequest request, String userAgent) {
        log.info("------------OtpServiceImpl:verifyOtp::verifying otp for {}--------",request.getEmail());
        String storedOtp = getStoredOtp(request.getEmail());

        OtpResponse response = new OtpResponse();
        if(!Objects.isNull(storedOtp)) {
            if(storedOtp.equals(request.getOtp())){
                User user = userService.findUserByEmail(request.getEmail());

                if (user.getRole().equals(Constants.DOCTOR)){
                    DoctorDetailsDao doctorDetails = doctorDetailsService.getDoctorByUserId(user.getId());
                    user.setDoctorDetails(doctorDetails);
                }

                Token token = authGateway.generateToken(Token.builder().userId(user.getId().toString()).role(user.getRole()).userAgent(userAgent).build());
                if (Objects.isNull(token)) {
                    throw new BusinessException(HttpStatus.SERVICE_UNAVAILABLE);
                }

                userService.updateUserVerification(user.getId());
                user.setOtpVerified(true);
                response.setUser(user);
                response.setAccessToken(token.getAccessToken());
                return response;
            }
        }

        return OtpResponse.builder()
                .status(Status.INVALID)
                .build();
    }

    private String getStoredOtp(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    private String generateOtp(String email) {
        SecureRandom random = new SecureRandom();
        int otpValue = random.nextInt(1000000);
        String otp = String.format("%06d", otpValue);

        //saving the userid and otp in redis cache
        saveOtpInRedis(email,otp);
        return otp;
    }

    private void saveOtpInRedis(String email, String otp) {
        log.info("----OtpServiceImpl:generateOtp::--otp is {}--stored in redis with key {}--", otp, email);
        redisTemplate.opsForValue().set(email, String.valueOf(otp), OTP_TTL, TimeUnit.MILLISECONDS);
    }
}

package com.telemedicine.user.controller;

import com.telemedicine.user.model.payload.request.OtpRequest;
import com.telemedicine.user.model.payload.response.OtpResponse;
import com.telemedicine.user.service.OtpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/otp")
public class OtpController {

    private final OtpService otpService;
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/verify")
    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody OtpRequest request, @RequestHeader("User-Agent") String userAgent){
        OtpResponse response = otpService.verifyOtp(request, userAgent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resend")
    public ResponseEntity<Void> resendMail(@Valid @RequestBody OtpRequest request){
        otpService.sendOtp(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package com.telemedicine.user.service;

import com.telemedicine.user.model.payload.request.OtpRequest;
import com.telemedicine.user.model.payload.response.OtpResponse;

public interface OtpService {
    void sendOtp(OtpRequest request);
    void sendOtp(String email);
    OtpResponse verifyOtp(OtpRequest request, String userAgent);
}

package com.telemedicine.user.model.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class NotificationRequest {
    private String to;
    private String subject;
    private String templateType;
    private Map<String,String> model;
}

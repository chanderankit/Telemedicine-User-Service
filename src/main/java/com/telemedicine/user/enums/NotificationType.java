package com.telemedicine.user.enums;

public enum NotificationType {

    EMAIL("EMAIL");

    private String value;

    public String getValue(){
        return this.value = value;
    };

    private NotificationType(String value) {
        this.value = value;
    }
}

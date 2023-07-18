package com.telemedicine.user.enums;

public enum Role {

    PATIENT("ROLE_PATIENT"),
    DOCTOR("ROLE_DOCTOR");

    private String value;

    public String getValue(){
        return this.value = value;
    };

    private Role(String value) {
        this.value = value;
    }
}

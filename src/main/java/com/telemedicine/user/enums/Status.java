package com.telemedicine.user.enums;

public enum Status {

    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    INVALID("INVALID");

    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    private Status(String value) {
        this.value = value;
    }
}

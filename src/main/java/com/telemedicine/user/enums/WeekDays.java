package com.telemedicine.user.enums;

public enum WeekDays {
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    private WeekDays(String value) {
        this.value = value;
    }
}

package com.telemedicine.user.enums;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    private String value;

    public String getValue(){
        return this.value = value;
    };

    private Gender(String value) {
        this.value = value;
    }
}

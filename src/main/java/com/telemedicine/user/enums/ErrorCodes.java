package com.telemedicine.user.enums;

public enum ErrorCodes {

    USER_NOT_FOUND("TELE_001"),
    CLINIC_NOT_FOUND("TELE_002"),
    SLOT_NOT_PRESENT("TELE_003"),
    USER_NOT_VERIFIED("TELE_004"),
    DOCTOR_NOT_FOUND("TELE_005"),
    INVALID_TIME("TELE_006"),
    SLOT_TIMING_CONFLICT("TELE_007"),
    TIME_ERROR("TELE_008"),
    DATE_TIME_EXCEPTION("TELE_009"),
    EMPTY_LIST("TELE_002"),
    CONNECTION_FAILED("TELE_003"),
    EMAIL_CANNOT_BE_CHANGED("Tele_004"),
    EMAIL_ALREADY_EXISTS("Tele_006"),
    USER_NOT_PRESENT("Tele_007");

    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    private ErrorCodes(String value) {
        this.value = value;
    }
}

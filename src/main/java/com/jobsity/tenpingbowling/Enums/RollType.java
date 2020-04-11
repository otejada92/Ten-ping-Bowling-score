package com.jobsity.tenpingbowling.Enums;

public enum  RollType {

    NORMAL("normal"),
    STRIKE("strike"),
    SPARE("spare"),
    FOUL("foul"),
    NONE("none");

    private  String value;

    RollType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

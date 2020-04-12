package com.jobsity.tenpingbowling.Enums;

public enum  RollType {

    NORMAL("normal"),
    STRIKE("strike"),
    SPARE("spare"),
    FOUL("foul"),
    NO_PINGS_KNOCKED("no pings knocked"),
    NONE("none");

    private  String value;

    RollType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

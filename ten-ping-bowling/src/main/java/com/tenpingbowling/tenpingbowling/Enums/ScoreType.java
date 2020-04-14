package com.tenpingbowling.tenpingbowling.Enums;

public enum ScoreType {

    NORMAL("normal"),
    STRIKE("strike"),
    SPARE("spare"),
    FOUL("foul"),
    NO_PINGS_KNOCKED("no pings knocked"),
    NONE("none");

    private  String value;

    ScoreType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

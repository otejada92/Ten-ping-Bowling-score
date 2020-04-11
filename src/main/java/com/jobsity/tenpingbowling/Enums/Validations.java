package com.jobsity.tenpingbowling.Enums;

/*
 THIS SHOULD BE SOME KIND OF SERVICES TO VALIDATES THIS, REQUEST AND RESPONSE PATTERN
 */
public enum Validations {

    PLAYER_INDEX(0),
    SCORE_INDEX(1),
    VALID_SCORE_LINE_LENGTH(2),
    VALID_KEY_MAP_SIZE(2);

    private final int value;

    Validations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

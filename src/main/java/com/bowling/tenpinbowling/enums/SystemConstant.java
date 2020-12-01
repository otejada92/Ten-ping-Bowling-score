package com.bowling.tenpinbowling.enums;

/**
 * Hold constant that ten pin bowling program uses.
 */
public enum SystemConstant {

    PLAYER_INDEX(0),
    SCORE_INDEX(1),
    VALID_SCORE_LINE_LENGTH(2),
    LAST_ROUND_ID(10);

    private final int value;

    SystemConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

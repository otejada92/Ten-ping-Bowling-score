package com.jobsity.tenpingbowling.models;

import com.jobsity.tenpingbowling.Enums.RollType;

public class Roll {

    private String score;
    private RollType rollType;

    public Roll(String score, RollType rollType) {
        this.score = score;
        this.rollType = rollType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public RollType getRollType() {
        return rollType;
    }

    public void setRollType(RollType rollType) {
        this.rollType = rollType;
    }
}

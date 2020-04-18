package com.bowling.tenpinbowling.models;

import com.bowling.tenpinbowling.Enums.ScoreType;

/**
 * This is a model class to hold Roll information
 *
 */
public class Roll {

    private String score;
    private ScoreType rollType;

    public Roll(String score, ScoreType rollType) {
        this.score = score;
        this.rollType = rollType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public ScoreType getRollType() {
        return rollType;
    }

    public void setRollType(ScoreType rollType) {
        this.rollType = rollType;
    }
}

package com.bowling.tenpinbowling.models;

import com.bowling.tenpinbowling.enums.ScoreType;

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
    
    public ScoreType getRollType() {
        return rollType;
    }

}

package com.bowling.tenpinbowling.models;

import com.bowling.tenpinbowling.Enums.ScoreType;

import java.util.Objects;


/**
 * This is a model class to hold Frame information
 *
 */
public class Frame {

    private int round;
    private Roll firstRoll;
    private Roll secondRoll;
    private Roll thirdRoll;
    private int bonusByScoreType;
    private int frameFinalScore;

    private ScoreType frameScoreType;

    private Frame(Builder builder) {
        this.round = builder.round;
        this.firstRoll = builder.firstRoll;
        this.secondRoll = builder.secondRoll;
        this.thirdRoll = builder.thirdRoll;
        this.bonusByScoreType = builder.bonusByScoreType;
        this.frameFinalScore = builder.frameFinalScore;
        this.frameScoreType = builder.frameScoreType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frame frame = (Frame) o;
        return round == frame.round;
    }

    @Override
    public int hashCode() {
        return Objects.hash(round);
    }

    public int getRound() {
        return round;
    }

    public Roll getFirstRoll() {
        return firstRoll;
    }

    public Roll getSecondRoll() {
        return secondRoll;
    }

    public Roll getThirdRoll() {
        return thirdRoll;
    }

    public int getFrameFinalScore() {
        return frameFinalScore;
    }

    public void setFrameFinalScore(int frameFinalScore) {
        this.frameFinalScore = frameFinalScore;
    }

    public ScoreType getFrameScoreType() {
        return frameScoreType;
    }

    public int getBonusByScoreType() {
        return bonusByScoreType;
    }

    public void setBonusByScoreType(int bonusByScoreType) {
        this.bonusByScoreType = bonusByScoreType;
    }

    public static class Builder {

        int round;

        Roll firstRoll;
        Roll secondRoll;
        Roll thirdRoll;
        int bonusByScoreType;
        int frameFinalScore;

        ScoreType frameScoreType;

        public  Builder(){}

        public Roll getFirstRoll() {
            return firstRoll;
        }

        public Roll getSecondRoll() {
            return secondRoll;
        }

        public ScoreType getFrameScoreType() {
            return frameScoreType;
        }

        public int getRound(){return  round;}

        public Builder round(int round){this.round = round; return  this;}
        public Builder firstRoll(Roll firstRoll){this.firstRoll = firstRoll; return  this;}
        public Builder secondRoll(Roll secondRoll){this.secondRoll = secondRoll; return  this;}
        public Builder thirdRoll(Roll thirdRoll){this.thirdRoll = thirdRoll; return  this;}
        public Builder frameScoreType(ScoreType frameScoreType){this.frameScoreType = frameScoreType; return  this;}

        public Frame build(){return new Frame(this);}
    }
}

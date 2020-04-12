package com.jobsity.tenpingbowling.models;


import com.jobsity.tenpingbowling.Enums.ScoreType;

public class Frame {

    private int round;
    private Roll firstRoll;
    private Roll secondRoll;
    private Roll thirdRoll;
    private int bonusForTheNextRolls; // this will be define by the rolltype || (normal, strike, Spare)
    private int frameFinalScore;
    private final ScoreType frameScoreType;

    private Frame(Builder builder)
    {
        this.round = builder.round;
        this.firstRoll = builder.firstRoll;
        this.secondRoll = builder.secondRoll;
        this.thirdRoll = builder.thirdRoll;
        this.bonusForTheNextRolls = builder.bonusForTheNextRolls;
        this.frameFinalScore = builder.frameFinalScore;
        this.frameScoreType = builder.frameScoreType;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Roll getFirstRoll() {
        return firstRoll;
    }

    public void setFirstRoll(Roll firstRoll) {
        this.firstRoll = firstRoll;
    }

    public Roll getSecondRoll() {
        return secondRoll;
    }

    public void setSecondRoll(Roll secondRoll) {
        this.secondRoll = secondRoll;
    }

    public Roll getThirdRoll() {
        return thirdRoll;
    }

    public void setThirdRoll(Roll thirdRoll) {
        this.thirdRoll = thirdRoll;
    }

    public int getBonusForTheNextRolls() {
        return bonusForTheNextRolls;
    }

    public void setBonusForTheNextRolls(int bonusForTheNextRolls) {
        this.bonusForTheNextRolls = bonusForTheNextRolls;
    }

    public int getFrameFinalScore() {
        return frameFinalScore;
    }

    public void setFrameFinalScore(int frameFinalScore) {
        this.frameFinalScore = frameFinalScore;
    }

    public static class Builder {

        int round;

        Roll firstRoll;
        Roll secondRoll;
        Roll thirdRoll;
        int bonusForTheNextRolls;
        int frameFinalScore;
        ScoreType frameScoreType;


        public  Builder(){}

        public Roll getFirstRoll() {
            return firstRoll;
        }

        public void setFirstRoll(Roll firstRoll) {
            this.firstRoll = firstRoll;
        }

        public Roll getSecondRoll() {
            return secondRoll;
        }

        public void setSecondRoll(Roll secondRoll) {
            this.secondRoll = secondRoll;
        }

        public Roll getThirdRoll() {
            return thirdRoll;
        }

        public void setThirdRoll(Roll thirdRoll) {
            this.thirdRoll = thirdRoll;
        }

        public ScoreType getFrameScoreType() {
            return frameScoreType;
        }

        public void setFrameScoreType(ScoreType frameScoreType) {
            this.frameScoreType = frameScoreType;
        }

        public Builder round(int round){this.round = round; return  this;}
        public Builder firstRoll(Roll firstRoll){this.firstRoll = firstRoll; return  this;}
        public Builder secondRoll(Roll secondRoll){this.secondRoll = secondRoll; return  this;}
        public Builder thirdRoll(Roll thirdRoll){this.thirdRoll = thirdRoll; return  this;}
        public Builder frameFinalScore(int frameFinalScore){this.frameFinalScore = frameFinalScore; return  this;}
        public Builder frameScoreType(ScoreType frameScoreType){this.frameScoreType = frameScoreType; return  this;}

        public Frame build(){return new Frame(this);}
    }
}

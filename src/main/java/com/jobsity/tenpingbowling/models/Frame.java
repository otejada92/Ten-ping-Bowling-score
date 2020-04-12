package com.jobsity.tenpingbowling.models;


public class Frame {

    private int round;
    private Roll rollOne;
    private Roll rollTwo;
    private Roll rollThree;
    private int bonusForTheNextRolls; // this will be define by the rolltype || (normal, strike, Spare)
    private Frame(Builder builder)
    {
        this.round = builder.round;
        this.rollOne = builder.rollOne;
        this.rollTwo = builder.rollTwo;
        this.rollThree = builder.rollThree;
        this.bonusForTheNextRolls = builder.bonusForTheNextRolls;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Roll getRollOne() {
        return rollOne;
    }

    public void setRollOne(Roll rollOne) {
        this.rollOne = rollOne;
    }

    public Roll getRollTwo() {
        return rollTwo;
    }

    public void setRollTwo(Roll rollTwo) {
        this.rollTwo = rollTwo;
    }

    public int getBonusForTheNextRolls() {
        return bonusForTheNextRolls;
    }

    public void setBonusForTheNextRolls(int bonusForTheNextRolls) {
        this.bonusForTheNextRolls = bonusForTheNextRolls;
    }

    public Roll getRollThree() {
        return rollThree;
    }

    public void setRollThree(Roll rollThree) {
        this.rollThree = rollThree;
    }


    public static class Builder {

        int round;
        Roll rollOne;
        Roll rollTwo;
        int bonusForTheNextRolls;
        Roll rollThree;

        public  Builder(){}

        public Builder round(int round){this.round = round; return  this;}
        public Builder rollOne(Roll rollOne){this.rollOne = rollOne; return  this;}
        public Builder rollTwo(Roll rollTwo){this.rollTwo = rollTwo; return  this;}
        public Builder rollThree(Roll rollThree){this.rollThree = rollThree; return  this;}

        public Frame build(){return new Frame(this);}
    }
}

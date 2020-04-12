package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Roll;


public interface RollScoreServices {

    Roll getRoll(String rollScore, boolean isPreviousRollFoul, boolean isSpareRoll);
    default boolean isStrikeRoll(String rollScore){
        return rollScore.equals("10");
    }
    default boolean isSpareRoll(String firstRollScore, String secondRollScore){
        if (isNormalRoll(firstRollScore) && isNormalRoll(secondRollScore))
            return Integer.parseInt(firstRollScore) + Integer.parseInt(secondRollScore) == 10;
        else return isFoulRoll(firstRollScore) && isStrikeRoll(secondRollScore);
    }
    default boolean isNormalRoll(String rollScore){
        return  rollScore.matches("[0-9]");
    }
    default boolean isFoulRoll(String rollScore)
    {
        return  rollScore.toLowerCase().equals("f");
    }
    default boolean notPingKnocked(String rollScore)
    {
        return rollScore.toLowerCase().equals("0");
    }

}

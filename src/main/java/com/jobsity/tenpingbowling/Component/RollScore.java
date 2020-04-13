package com.jobsity.tenpingbowling.Component;

import com.jobsity.tenpingbowling.models.Roll;
import org.springframework.stereotype.Component;

import static com.jobsity.tenpingbowling.Enums.ScoreType.*;

@Component
public class RollScore {

    public Roll getRoll(String rollScore, boolean isPreviousRollFoul, boolean isSpareRoll) {
        Roll roll;

        if(isFoulRoll(rollScore))
            roll = new Roll(rollScore, FOUL);
        else if(notPingKnocked(rollScore))
            return new Roll(rollScore, NO_PINGS_KNOCKED);
        else if(isStrikeRoll(rollScore) || isSpareRoll)
            roll = new Roll(rollScore, (isPreviousRollFoul || isSpareRoll) ? SPARE : STRIKE);
        else
            roll = new Roll(rollScore, NORMAL);

        return  roll;
    }

     private boolean isStrikeRoll(String rollScore){
        return rollScore.equals("10");
    }

     public boolean isSpareRoll(String firstRollScore, String secondRollScore){

        if (isNormalRoll(firstRollScore) && isNormalRoll(secondRollScore))
            return Integer.parseInt(firstRollScore) + Integer.parseInt(secondRollScore) == 10;
        else return isFoulRoll(firstRollScore) && isStrikeRoll(secondRollScore);

    }

     private boolean isNormalRoll(String rollScore){
        return  rollScore.matches("[0-9]");
    }

    public boolean isFoulRoll(String rollScore) {
        return  rollScore.toLowerCase().equals("f");
    }

    private boolean notPingKnocked(String rollScore) {
        return rollScore.toLowerCase().equals("0");
    }

}

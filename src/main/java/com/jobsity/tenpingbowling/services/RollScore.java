package com.jobsity.tenpingbowling.services;

import com.jobsity.tenpingbowling.interfaces.RollScoreServices;
import com.jobsity.tenpingbowling.models.Roll;
import org.springframework.stereotype.Service;

import static com.jobsity.tenpingbowling.Enums.RollType.*;

@Service
public class RollScore implements RollScoreServices {

    public Roll getRoll(String rollScore, boolean isPreviousRollFoul, boolean isSpareRoll)
    {
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

}

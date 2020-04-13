package com.jobsity.tenpingbowling.Component;

import com.jobsity.tenpingbowling.models.Roll;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

@Component
public class ScoreParse {

    int parseRollScoreToInteger(String score){

        return  parseRollScore(getEscapedRollScore(score));
    }

    private int parseRollScore(String value){ return Integer.parseInt(value);}

    private String getEscapedRollScore(String rollScore) {

        if (rollScore != null)
        {
            if (rollScore.matches(".*[f F].*"))
                rollScore = "0";
            if (rollScore.matches(".*[x X /].*"))
                rollScore = "10";
        }
        return rollScore;
    }

    public String parseRollScoreForBoard(Roll... rollsArray)
    {
        StringJoiner  value = new StringJoiner(" ");
        ArrayList<Roll> rolls = new ArrayList<>(Arrays.asList(rollsArray));

        for (Roll roll : rolls)
        {
            switch (roll.getRollType())
            {
                case STRIKE:
                    value.add("X");
                    break;
                case SPARE:
                    value.add("/");
                    break;
                case FOUL:
                    value.add("F");
                    break;
                case NORMAL:
                    value.add(roll.getScore());
                    break;
                case NO_PINGS_KNOCKED:
                    value.add("0");
                    break;
                default:
                    break;
            }
        }
        value.add(",");
        return value.toString();
    }

}

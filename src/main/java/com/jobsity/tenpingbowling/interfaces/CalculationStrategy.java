package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Roll;
import org.springframework.lang.Nullable;

public interface CalculationStrategy {

    int calculateScore(Frame unProcessedFrame, @Nullable Frame... pendingFrames);

    default String getEscapedRollValue(Roll roll, String defaultValue) {

        String value = defaultValue;
        if (roll != null)
        {
            value = roll.getScore();

            if (value.matches(".*[f F].*"))
                return "0";
            if (value.matches(".*[x X /].*"))
                return "10";
        }

        return  value;
    }

    default int getIntegerRollScore(Roll roll, String defaultValue){

        return  parseRollValue(getEscapedRollValue(roll, defaultValue));
    }

    default int parseRollValue(String value){ return Integer.parseInt(value);}
}

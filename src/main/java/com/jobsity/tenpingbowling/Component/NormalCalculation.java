package com.jobsity.tenpingbowling.Component;

import com.jobsity.tenpingbowling.interfaces.CalculationStrategy;
import com.jobsity.tenpingbowling.models.Frame;
import org.springframework.stereotype.Component;

@Component
public class NormalCalculation implements CalculationStrategy {

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int firsRollValue = getIntegerRollScore(unProcessedFrame.getFirstRoll(), "0");
        int secondRollValue = getIntegerRollScore(unProcessedFrame.getSecondRoll(), "0");

        return Integer.sum(firsRollValue, secondRollValue);
    }
}

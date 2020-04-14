package com.Component;

import com.interfaces.CalculationStrategy;
import com.models.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NormalCalculation implements CalculationStrategy {

    @Autowired
    private  ScoreParse scoreParse;

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int firsRollValue = scoreParse.parseRollScoreToInteger(unProcessedFrame.getFirstRoll().getScore());
        int secondRollValue = scoreParse.parseRollScoreToInteger(unProcessedFrame.getSecondRoll().getScore());

        return Integer.sum(firsRollValue, secondRollValue);
    }
}

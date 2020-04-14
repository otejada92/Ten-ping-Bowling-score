package com.Component;

import com.interfaces.CalculationStrategy;
import com.models.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class SpareCalculation implements CalculationStrategy {

    @Autowired
    @Qualifier("normalCalculation")
    private CalculationStrategy normalCalculation;

    @Autowired
    @Qualifier(value = "strikeCalculation")
    private CalculationStrategy strikeCalculation;

    @Autowired
    private  ScoreParse scoreParse;

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int unProcessedFrameScore = normalCalculation.calculateScore(unProcessedFrame);
        Frame nextFrame  = pendingFrames[0];
        int extraPoint;

        switch (nextFrame.getFrameScoreType())
        {
            case STRIKE:
            {
                strikeCalculation.calculateScore(unProcessedFrame, pendingFrames);
            }
            default:
            {
                extraPoint = scoreParse.parseRollScoreToInteger(nextFrame.getFirstRoll().getScore());
                break;
            }
        }

        return Integer.sum(unProcessedFrameScore, extraPoint);

    }
}

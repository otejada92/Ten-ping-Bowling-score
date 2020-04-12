package com.jobsity.tenpingbowling.Component;

import com.jobsity.tenpingbowling.interfaces.CalculationStrategy;
import com.jobsity.tenpingbowling.models.Frame;
import org.springframework.stereotype.Component;

@Component
public class NormalCalculation implements CalculationStrategy {

    @Override
    public Frame calculateScore(Frame frame) {

        int firsRollValue = Integer.parseInt(frame.getFirstRoll().getScore());
        int secondRollValue = Integer.parseInt(frame.getSecondRoll().getScore());

        int frameScore = firsRollValue + secondRollValue;

        return buildFrameCalculated(frame, frameScore);
    }

    @Override
    public Frame buildFrameCalculated(Frame frame, int frameScore) {

        return new Frame.Builder().round(frame.getRound())
                .firstRoll(frame.getFirstRoll())
                .secondRoll(frame.getSecondRoll())
                .thirdRoll(frame.getThirdRoll())
                .frameFinalScore(frameScore).build();
    }
}

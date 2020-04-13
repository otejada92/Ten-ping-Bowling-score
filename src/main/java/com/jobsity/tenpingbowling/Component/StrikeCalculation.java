package com.jobsity.tenpingbowling.Component;

import com.jobsity.tenpingbowling.Enums.ScoreType;
import com.jobsity.tenpingbowling.interfaces.CalculationStrategy;
import com.jobsity.tenpingbowling.models.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;


@Component
public class StrikeCalculation implements CalculationStrategy {

    @Autowired
    @Qualifier("normalCalculation")
    private CalculationStrategy normalCalculation;

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int scoreCalculated = 0;
        int unProcessedFrameScore = normalCalculation.calculateScore(unProcessedFrame);
        Frame frameAfterFrameToCalculate = pendingFrames[0];

        if (frameAfterFrameToCalculate.getFrameScoreType() == ScoreType.STRIKE)
        {
            int scoreSecondStrike = normalCalculation.calculateScore(frameAfterFrameToCalculate);
            int  extraScore = 0;

            Frame frameAfterSecondStrike = pendingFrames[(pendingFrames.length > 1) ? 1 : 0];

            if ((pendingFrames.length > 1) )
                extraScore = getIntegerRollScore(frameAfterSecondStrike.getFirstRoll(), "0");

            scoreCalculated = Stream.of(unProcessedFrameScore, scoreSecondStrike, extraScore).mapToInt(Integer::intValue).sum();

        }
        else
        {
            scoreCalculated = Integer.sum(normalCalculation.calculateScore(frameAfterFrameToCalculate), unProcessedFrameScore);
        }

        return  scoreCalculated;
    }
}
package com.tenpingbowling.tenpingbowling.Component;


import com.tenpingbowling.tenpingbowling.Enums.ScoreType;
import com.tenpingbowling.tenpingbowling.interfaces.CalculationStrategy;
import com.tenpingbowling.tenpingbowling.models.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;


@Component
public class StrikeCalculation implements CalculationStrategy {

    @Autowired
    @Qualifier("normalCalculation")
    private CalculationStrategy normalCalculation;

    @Autowired
    private  ScoreParse scoreParse;

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int scoreCalculated;
        int unProcessedFrameScore = normalCalculation.calculateScore(unProcessedFrame);
        Frame frameAfterFrameToCalculate = pendingFrames[0];

        if (frameAfterFrameToCalculate.getFrameScoreType() == ScoreType.STRIKE)
        {
            int scoreSecondStrike = normalCalculation.calculateScore(frameAfterFrameToCalculate);
            int  extraScore = 0;

            Frame frameAfterSecondStrike = pendingFrames[(pendingFrames.length > 1) ? 1 : 0];

            if ((pendingFrames.length > 1) )
                extraScore = scoreParse.parseRollScoreToInteger(frameAfterSecondStrike.getFirstRoll().getScore());

            scoreCalculated = Stream.of(unProcessedFrameScore, scoreSecondStrike, extraScore).mapToInt(Integer::intValue).sum();

        }
        else
        {
            scoreCalculated = Integer.sum(normalCalculation.calculateScore(frameAfterFrameToCalculate), unProcessedFrameScore);
        }

        return  scoreCalculated;
    }
}

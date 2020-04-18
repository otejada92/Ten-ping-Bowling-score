package com.bowling.tenpinbowling.scoreprocessors;

import com.bowling.tenpinbowling.interfaces.ProcessorStrategy;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.Enums.ScoreType;
import com.bowling.tenpinbowling.scoreprocessors.common.ScoreProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;


@Component
public class StrikeScoreProcessor extends ScoreProcessor {

    @Autowired
    @Qualifier("normalScoreProcessor")
    private ProcessorStrategy normalScoreProcessor;

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int scoreCalculated;
        int unProcessedFrameScore = normalScoreProcessor.calculateScore(unProcessedFrame);
        Frame frameAfterFrameToCalculate = pendingFrames[0];

        if (frameAfterFrameToCalculate.getFrameScoreType() == ScoreType.STRIKE)
        {
            int scoreSecondStrike = normalScoreProcessor.calculateScore(frameAfterFrameToCalculate);
            int  extraScore = 0;

            Frame frameAfterSecondStrike = pendingFrames[(pendingFrames.length > 1) ? 1 : 0];

            if ((pendingFrames.length > 1) )
                extraScore = parseRollScoreToInteger(frameAfterSecondStrike.getFirstRoll().getScore());

            scoreCalculated = Stream.of(unProcessedFrameScore, scoreSecondStrike, extraScore).mapToInt(Integer::intValue).sum();

        }
        else
        {
            scoreCalculated = Integer.sum(normalScoreProcessor.calculateScore(frameAfterFrameToCalculate), unProcessedFrameScore);
        }

        return  scoreCalculated;
    }
}

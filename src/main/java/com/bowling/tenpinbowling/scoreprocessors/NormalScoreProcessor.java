package com.bowling.tenpinbowling.scoreprocessors;

import com.bowling.tenpinbowling.models.Frame;
import org.springframework.stereotype.Component;

@Component
public class NormalScoreProcessor extends ScoreProcessor  {

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int firsRollValue = parseRollScoreToInteger(unProcessedFrame.getFirstRoll().getScore());
        int secondRollValue = parseRollScoreToInteger(unProcessedFrame.getSecondRoll().getScore());

        return Integer.sum(firsRollValue, secondRollValue);
    }
}

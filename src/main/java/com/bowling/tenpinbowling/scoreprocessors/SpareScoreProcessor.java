package com.bowling.tenpinbowling.scoreprocessors;

import com.bowling.tenpinbowling.interfaces.ProcessorStrategy;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.scoreprocessors.common.ScoreProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class SpareScoreProcessor extends ScoreProcessor {

    @Autowired
    @Qualifier("normalScoreProcessor")
    private ProcessorStrategy normalScoreProcessor;

    SpareScoreProcessor() {
        super();
    }

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int unProcessedFrameScore = normalScoreProcessor.calculateScore(unProcessedFrame);
        Frame nextFrame = pendingFrames[0];
        int extraPoint;

        extraPoint = parseRollScoreToInteger(nextFrame.getFirstRoll().getScore());
        return Integer.sum(unProcessedFrameScore, extraPoint);

    }
}

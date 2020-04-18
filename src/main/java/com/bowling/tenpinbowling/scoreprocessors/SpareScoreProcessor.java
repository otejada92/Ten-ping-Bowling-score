package com.bowling.tenpinbowling.scoreprocessors;

import com.bowling.tenpinbowling.interfaces.ProcessorStrategy;
import com.bowling.tenpinbowling.models.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class SpareScoreProcessor extends ScoreProcessor {

    @Autowired
    @Qualifier("normalScoreProcessor")
    private ProcessorStrategy normalScoreProcessor;

    @Autowired
    @Qualifier(value = "strikeScoreProcessor")
    private ProcessorStrategy strikeScoreProcessor;

    private SpareScoreProcessor() {
        super();
    }

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... pendingFrames) {

        int unProcessedFrameScore = normalScoreProcessor.calculateScore(unProcessedFrame);
        Frame nextFrame  = pendingFrames[0];
        int extraPoint;

        switch (nextFrame.getFrameScoreType())
        {
            case STRIKE:
            {
                strikeScoreProcessor.calculateScore(unProcessedFrame, pendingFrames);
            }
            default:
            {
                extraPoint = parseRollScoreToInteger(nextFrame.getFirstRoll().getScore());
                break;
            }
        }

        return Integer.sum(unProcessedFrameScore, extraPoint);

    }
}

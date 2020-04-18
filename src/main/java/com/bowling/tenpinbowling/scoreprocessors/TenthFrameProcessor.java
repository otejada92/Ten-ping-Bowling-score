package com.bowling.tenpinbowling.scoreprocessors;

import com.bowling.tenpinbowling.models.Frame;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TenthFrameProcessor extends ScoreProcessor {

    @Override
    public int calculateScore(Frame unProcessedFrame, Frame... extraFrames) {

        int unProcessedFrameScore;

        unProcessedFrameScore = Stream.of(unProcessedFrame.getFirstRoll(), unProcessedFrame.getSecondRoll(), unProcessedFrame.getThirdRoll())
                .mapToInt(roll -> parseRollScoreToInteger(roll.getScore()))
                .sum();

        return unProcessedFrameScore;
    }
}

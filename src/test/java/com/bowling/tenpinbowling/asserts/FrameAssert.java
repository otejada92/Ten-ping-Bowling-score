package com.bowling.tenpinbowling.asserts;

import com.bowling.tenpinbowling.models.Frame;
import org.assertj.core.api.AbstractAssert;

public class FrameAssert extends AbstractAssert<FrameAssert, Frame> {

     FrameAssert(Frame actual) {
        super(actual, FrameAssert.class);
    }

    public FrameAssert frameScoreEqual(int score)
    {
        isNotNull();
        if (actual.getFrameFinalScore() != score) {
            failWithMessage("Expected frame to have a score %d but was %d",
                    score, actual.getFrameFinalScore());
        }
        return this;
    }
}

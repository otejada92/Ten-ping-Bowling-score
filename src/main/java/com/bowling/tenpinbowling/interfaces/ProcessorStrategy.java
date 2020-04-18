package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import org.springframework.lang.Nullable;

public interface ProcessorStrategy {

    int calculateScore(Frame unProcessedFrame, @Nullable Frame... pendingFrames);

}

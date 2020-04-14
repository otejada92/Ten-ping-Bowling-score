package com.tenpingbowling.interfaces;

import com.tenpingbowling.models.Frame;
import org.springframework.lang.Nullable;

public interface CalculationStrategy {

    int calculateScore(Frame unProcessedFrame, @Nullable Frame... pendingFrames);

}

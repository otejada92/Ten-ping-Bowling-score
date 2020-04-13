package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Frame;
import org.springframework.lang.Nullable;

public interface CalculationStrategy {

    int calculateScore(Frame unProcessedFrame, @Nullable Frame... pendingFrames);

}

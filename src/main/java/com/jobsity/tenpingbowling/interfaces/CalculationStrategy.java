package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Frame;

public interface CalculationStrategy {

    Frame calculateScore(Frame frame) throws CloneNotSupportedException;
    Frame buildFrameCalculated(Frame frame, int frameScore);
}

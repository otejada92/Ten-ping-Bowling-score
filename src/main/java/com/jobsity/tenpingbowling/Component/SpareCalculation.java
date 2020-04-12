package com.jobsity.tenpingbowling.Component;

import com.jobsity.tenpingbowling.interfaces.CalculationStrategy;
import com.jobsity.tenpingbowling.models.Frame;
import org.springframework.stereotype.Component;


@Component
public class SpareCalculation implements CalculationStrategy {

    @Override
    public Frame calculateScore(Frame frame) {
        return null;
    }

    @Override
    public Frame buildFrameCalculated(Frame frame, int frameScore) {
        return null;
    }
}

package com.jobsity.tenpingbowling.services;


import com.jobsity.tenpingbowling.interfaces.CalculationStrategy;
import com.jobsity.tenpingbowling.interfaces.FrameScoreCalculatorServices;
import com.jobsity.tenpingbowling.interfaces.ScoreMapServices;
import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;


@Service
public class FrameScoreCalculator implements FrameScoreCalculatorServices {

    private Map<Player, ArrayList<Frame>> frameMap;

    @Autowired
    @Qualifier("normalCalculation")
    CalculationStrategy normalCalculation;

    @Autowired
    @Qualifier("spareCalculation")
    CalculationStrategy spareCalculation;

    @Autowired
    @Qualifier("strikeCalculation")
    CalculationStrategy strikeCalculation;

    @Override
    public void calculateFrameScore() {

        for (Player player : frameMap.keySet())
        {
            ArrayList<Frame> playerFrames = frameMap.get(player);


        }

    }

    @Autowired
    public void setFrameMap(ScoreMapServices mapScoreServices) {
        this.frameMap = mapScoreServices.buildScoreMap();
    }
}

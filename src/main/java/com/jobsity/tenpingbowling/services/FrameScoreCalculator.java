package com.jobsity.tenpingbowling.services;


import com.jobsity.tenpingbowling.Enums.SystemConstant;
import com.jobsity.tenpingbowling.interfaces.CalculationStrategy;
import com.jobsity.tenpingbowling.interfaces.FrameScoreCalculatorServices;
import com.jobsity.tenpingbowling.interfaces.ScoreMapServices;
import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;


@Service
public class FrameScoreCalculator implements FrameScoreCalculatorServices {

    @Autowired
    @Qualifier(value = "normalCalculation")
    private CalculationStrategy normalCalculation;

    @Autowired
    @Qualifier(value = "spareCalculation")
    private
    CalculationStrategy spareCalculation;

    @Autowired
    @Qualifier(value = "strikeCalculation")
    private CalculationStrategy strikeCalculation;

    @Autowired
    @Qualifier(value = "tenthFrameCalculation")
    private CalculationStrategy tenthFrameCalculation;

    private Map<Player, ArrayList<Frame>> frameMap;

    @Override
    public void calculateFrameScore() {

        for (Player player : frameMap.keySet())
        {
            System.out.println("Calculating score from: " + player.getName());

            ArrayList<Frame> frames = frameMap.get(player);

            ArrayList<Frame> calculateFrameScore = calculateFrameScore(frames);

        }
    }

    private ArrayList<Frame> calculateFrameScore(ArrayList<Frame> frames) {

        ArrayList<Frame> scoredFrame = new ArrayList<>();
        ListIterator<Frame> framesIterator = frames.listIterator();
        ArrayList<Frame> shallowFrameCopy = new ArrayList<>(frames);
        int scoredCalculated = 0;

        do
        {
            Frame currentFrame = framesIterator.next();
            shallowFrameCopy.remove(currentFrame);

            if (currentFrame.getRound() != SystemConstant.LAST_ROUND_ID.getValue())
            {
                switch (currentFrame.getFrameScoreType())
                {
                    case STRIKE:
                    {
                        Frame[] pendingFrames = shallowFrameCopy.toArray(new Frame[0]);
                        scoredCalculated += strikeCalculation.calculateScore(currentFrame, pendingFrames);
                        break;
                    }
                    case SPARE:
                    {
                        scoredCalculated += spareCalculation.calculateScore(currentFrame, framesIterator.next());
                        framesIterator.previous();
                        break;
                    }
                    default:
                    {
                        scoredCalculated += normalCalculation.calculateScore(currentFrame);
                        break;
                    }
                }
            }
            else
            {
                scoredCalculated += tenthFrameCalculation.calculateScore(currentFrame);
            }

            currentFrame.setFrameFinalScore(scoredCalculated);
            scoredFrame.add(currentFrame);
        }while (framesIterator.hasNext());

        return  scoredFrame;
    }

    @Autowired
    public void setFrameMap(ScoreMapServices mapScoreServices) {
        this.frameMap = mapScoreServices.buildScoreMap();
    }
}
package com.services;


import com.Enums.SystemConstant;
import com.interfaces.CalculationStrategy;
import com.interfaces.ScoreFrameCalculatorService;
import com.interfaces.ScoreMapService;
import com.models.Frame;
import com.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;


@Service
public class ScoreFrameCalculator implements ScoreFrameCalculatorService {

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

    @Autowired
    private ScoreMapService mapScoreServices;

    private Map<Player, ArrayList<Frame>> frameMap;

    @Override
    public Map<Player, ArrayList<Frame>> calculateFrameScore() {

        frameMap =  mapScoreServices.getScoreMap();

        for (Player player : frameMap.keySet())
        {
            ArrayList<Frame> frames = frameMap.get(player);

            ArrayList<Frame> calculateFrameScore = calculateFrameScore(frames);

            frameMap.put(player, calculateFrameScore);
        }

        return frameMap;
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
}

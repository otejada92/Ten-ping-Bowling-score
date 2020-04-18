package com.bowling.tenpinbowling.services;


import com.bowling.tenpinbowling.Enums.SystemConstant;
import com.bowling.tenpinbowling.interfaces.ProcessorStrategy;
import com.bowling.tenpinbowling.interfaces.ScoreFrameProcessorService;
import com.bowling.tenpinbowling.interfaces.ScoreParseService;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Calculate Frame score.
 */
@Service
public class ScoreFrameProcessor implements ScoreFrameProcessorService {

    @Autowired
    @Qualifier(value = "normalScoreProcessor")
    private ProcessorStrategy normalScoreProcessor;

    @Autowired
    @Qualifier(value = "spareScoreProcessor")
    private ProcessorStrategy spareScoreProcessor;

    @Autowired
    @Qualifier(value = "strikeScoreProcessor")
    private ProcessorStrategy strikeScoreProcessor;

    @Autowired
    @Qualifier(value = "tenthFrameProcessor")
    private ProcessorStrategy tenthFrameProcessor;

    @Override
    public Map<Player, ArrayList<Frame>> calculateFrameScore(Map<Player, ArrayList<Frame>> scoreMap) {

        Map<Player, ArrayList<Frame>> calculatedFrames = new HashMap<>();

        for (Player player : scoreMap.keySet())
        {
            ArrayList<Frame> frames = scoreMap.get(player);

            ArrayList<Frame> calculateFrameScore = calculateFrameScore(frames);

            calculatedFrames.put(player, calculateFrameScore);
        }

        return calculatedFrames;
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
                        scoredCalculated += strikeScoreProcessor.calculateScore(currentFrame, pendingFrames);
                        break;
                    }
                    case SPARE:
                    {
                        scoredCalculated += spareScoreProcessor.calculateScore(currentFrame, framesIterator.next());
                        framesIterator.previous();
                        break;
                    }
                    default:
                    {
                        scoredCalculated += normalScoreProcessor.calculateScore(currentFrame);
                        break;
                    }
                }
            }
            else
            {
                scoredCalculated += tenthFrameProcessor.calculateScore(currentFrame);
            }

            currentFrame.setFrameFinalScore(scoredCalculated);
            scoredFrame.add(currentFrame);
        }while (framesIterator.hasNext());

        return  scoredFrame;
    }


}

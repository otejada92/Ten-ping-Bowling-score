package com.jobsity.tenpingbowling.services;


import com.jobsity.tenpingbowling.interfaces.FrameScoreServices;
import com.jobsity.tenpingbowling.interfaces.RollScoreServices;
import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Roll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;

import static com.jobsity.tenpingbowling.Enums.RollType.*;


//TODO: REMOVE STATIC STRING LIKE 10

@Service
public class FrameScore implements FrameScoreServices
{
    private Roll firstRoll;
    private Roll secondRoll;
    private int frameRound = 1;

    @Autowired
    private RollScoreServices rollScoreServices;


    @Override
    public ArrayList<Frame> buildFrames(ArrayList<String> bowlingGameInformationByPlayerName) {

        ArrayList<Frame> frames = new ArrayList<>();

        Iterator<String> scoreIterator = bowlingGameInformationByPlayerName.iterator();
        do
        {
            Frame.Builder frameBuilder;
            Frame frame;
            String firstRollScore = scoreIterator.next();

            firstRoll = rollScoreServices.getRoll(firstRollScore, false, false);


            frameBuilder =  buildFrameBuilder(scoreIterator);

            frame = frameBuilder.round(frameRound).rollOne(firstRoll).build();
            frames.add(frame);
            frameRound++;

        }while (scoreIterator.hasNext());

        return  frames;
    }

    @Override
    public Frame.Builder buildFrameBuilder(Iterator<String> scoreIterator) {

        Frame.Builder frame = new Frame.Builder();
        if (frameRound == 6)
            System.out.println("stop here");

        switch (firstRoll.getRollType())
        {
            case STRIKE:
            {
                if (frameRound == 10)
                {
                    frame = addNextScoresIntoFrame(scoreIterator, frame);
                }
                else
                    secondRoll = new Roll("0", NONE);
                break;
            }
            default:
            {
                frame = addNextScoresIntoFrame(scoreIterator, frame);

                break;
            }
        }
        return frame;
    }

    private Frame.Builder addNextScoresIntoFrame(Iterator<String> scoreIterator, Frame.Builder frame) {

        String secondRollScore = scoreIterator.next();
        String extraRollScore;
        Roll extraRoll = null;

        secondRoll = rollScoreServices.getRoll(secondRollScore, rollScoreServices.isFoulRoll(firstRoll.getScore()), rollScoreServices.isSpareRoll(firstRoll.getScore(), secondRollScore));

        if (frameRound == 10)
        {
            if (firstRoll.getRollType() == STRIKE || secondRoll.getRollType() == SPARE)
            {
                extraRollScore = scoreIterator.next();
                extraRoll = rollScoreServices.getRoll(extraRollScore, rollScoreServices.isFoulRoll(secondRoll.getScore()), rollScoreServices.isSpareRoll(secondRoll.getScore(), extraRollScore));

            }
            else
                extraRoll = new Roll("0", NONE);

        }
        return frame.rollTwo(secondRoll).rollThree(extraRoll);
    }
}


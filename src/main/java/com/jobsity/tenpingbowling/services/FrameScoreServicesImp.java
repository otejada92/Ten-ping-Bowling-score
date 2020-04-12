package com.jobsity.tenpingbowling.services;


import com.jobsity.tenpingbowling.interfaces.FrameScoreServices;
import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Roll;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;

import static com.jobsity.tenpingbowling.Enums.RollType.*;
import static com.jobsity.tenpingbowling.Enums.RollType.FOUL;


//TODO: REMOVE STATIC STRING LIKE 10

@Service
public class FrameScoreServicesImp implements FrameScoreServices
{
    private Roll firstRoll;
    private Roll secondRoll;
    private  Roll extraRoll;

    private  String firstRollScore;
    private String secondRollScore;


    @Override
    public ArrayList<Frame> buildFrames(ArrayList<String> bowlingGameInformationByPlayerName) {

        int frameRound = 1;
        ArrayList<Frame> frames = new ArrayList<>();

        Iterator<String> scoreIterator = bowlingGameInformationByPlayerName.iterator();
        do
        {
            Frame.Builder frameBuilder;
            Frame frame;
            firstRollScore = scoreIterator.next();

            firstRoll = getRoll(firstRollScore, false, false);

            if (frameRound < 10)
            {

                frameBuilder =  buildCommonFrameBuilder(scoreIterator);
            }
            else
            {
                frameBuilder = build10thFrameBuilder(scoreIterator);
            }

            frame = frameBuilder.round(frameRound).build();

            frames.add(frame);
            frameRound++;

        }while (scoreIterator.hasNext());


        return  frames;
    }

    @Override
    public Frame.Builder buildCommonFrameBuilder(Iterator<String> scoreIterator) {
        switch (firstRoll.getRollType())
        {
            case STRIKE:
            {
                secondRoll = new Roll("0", NONE);
                break;
            }
            case FOUL:
            {
                secondRollScore = scoreIterator.next();
                secondRoll = getRoll(secondRollScore, true,false);
                break;
            }
            default:
                secondRollScore = scoreIterator.next();
                secondRoll = getRoll(secondRollScore, false, isSpareRoll(firstRollScore, secondRollScore));

                break;
        }
        return new Frame.Builder().rollOne(firstRoll).rollTwo(secondRoll);
    }

    @Override
    public Frame.Builder build10thFrameBuilder(Iterator<String> scoreIterator) {
        String extraRollScore;
        switch (firstRoll.getRollType())
        {
            case STRIKE:
            {
                secondRollScore = scoreIterator.next();

                if (isStrikeRoll(secondRollScore))
                {
                    secondRoll = getRoll(secondRollScore, false, false);
                    extraRollScore = scoreIterator.next();

                    extraRoll = getRoll(extraRollScore, false, false);

                }
                else if (isFoulRoll(secondRollScore))
                {
                    secondRoll = getRoll(secondRollScore, false, false);

                    extraRollScore = scoreIterator.next();
                    extraRoll = getRoll(extraRollScore, true, isSpareRoll(secondRollScore, extraRollScore));
                }
                else
                {
                    secondRoll = getRoll(secondRollScore, false, isSpareRoll(firstRollScore, secondRollScore));

                    extraRollScore = scoreIterator.next();

                    extraRoll = getRoll(extraRollScore, false, isSpareRoll(secondRollScore, extraRollScore));
                }
                break;
            }
            case FOUL:
            {

                secondRollScore = scoreIterator.next();
                if (isStrikeRoll(secondRollScore))
                {
                    secondRoll = getRoll(secondRollScore, true, isSpareRoll(firstRollScore, secondRollScore));
                    extraRollScore = scoreIterator.next();

                    extraRoll = getRoll(extraRollScore, false, false);
                }
                else if (isFoulRoll(secondRollScore))
                {
                    secondRoll = getRoll(secondRollScore, true, false);
                }
                else
                {
                    secondRoll = getRoll(secondRollScore, true, isSpareRoll(firstRollScore, secondRollScore));
                }
                break;
            }
            default:
            {
                secondRollScore = scoreIterator.next();
                if (isStrikeRoll(secondRollScore))
                {
                    secondRoll = getRoll(secondRollScore, false, isSpareRoll(firstRollScore, secondRollScore));

                    extraRollScore = scoreIterator.next();
                    extraRoll = getRoll(extraRollScore, false, false);
                }
                else if (isFoulRoll(secondRollScore))
                {
                    secondRoll = getRoll(secondRollScore, true, false);
                }
                else
                {
                    secondRoll = getRoll(secondRollScore, false, isSpareRoll(firstRollScore, secondRollScore));
                }
                break;
            }
        }
        return new Frame.Builder().rollOne(firstRoll).rollTwo(secondRoll).rollThree(extraRoll);
    }
    
    private boolean isStrikeRoll(String rollScore){
        return rollScore.equals("10");
    }
    private boolean isSpareRoll(String firstRollScore, String secondRollScore){
        return  Integer.parseInt(firstRollScore) + Integer.parseInt(secondRollScore) == 10;
    }
    private boolean isNormalRoll(String rollScore){
        return  rollScore.matches("[1-9]");
    }
    private boolean isFoulRoll(String rollScore)
    {
        return  rollScore.toLowerCase().equals("f") || rollScore.toLowerCase().equals("0");
    }

    private  Roll getRoll(String rollScore, boolean isPreviousRollFoul, boolean isSpareRoll)
    {
        Roll roll;

        if(isStrikeRoll(rollScore) || isSpareRoll)
        {
            roll = new Roll(rollScore, (isPreviousRollFoul || isSpareRoll) ? SPARE : STRIKE);
        }
        else if(isFoulRoll(rollScore))
        {
            roll = new Roll(rollScore, FOUL);
        }
        else
        {
            roll = new Roll(rollScore, NORMAL);
        }

        return  roll;
    }
}


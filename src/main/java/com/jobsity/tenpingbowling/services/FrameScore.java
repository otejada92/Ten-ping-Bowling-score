package com.jobsity.tenpingbowling.services;

import com.jobsity.tenpingbowling.Component.RollScore;
import com.jobsity.tenpingbowling.Enums.ScoreType;
import  com.jobsity.tenpingbowling.Enums.SystemConstant;
import com.jobsity.tenpingbowling.interfaces.FrameScoreServices;
import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Roll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BinaryOperator;


//TODO: REMOVE STATIC STRING LIKE 10

@Service
public class FrameScore implements FrameScoreServices
{
    private Roll firstRoll;
    private Roll secondRoll;
    private int frameRound = 1;

    @Autowired
    private RollScore rollScore;

    @Override
    public ArrayList<Frame> buildFrames(ArrayList<String> bowlingGameInformationByPlayerName) {

        ArrayList<Frame> frames = new ArrayList<>();

        Iterator<String> scoreIterator = bowlingGameInformationByPlayerName.iterator();
        do
        {
            Frame frame;
            String firstRollScore = scoreIterator.next();

            firstRoll = rollScore.getRoll(firstRollScore, false, false);


            frame =  buildFrameBuilder(scoreIterator);

            frames.add(frame);
            frameRound++;

        }while (scoreIterator.hasNext());

        return  frames;
    }

    @Override
    public Frame buildFrameBuilder(Iterator<String> scoreIterator) {

        Frame.Builder frameBuilder = new Frame.Builder();
        frameBuilder.round(frameRound).firstRoll(firstRoll).build();
        boolean isLastRound = isLastRound();

        switch (firstRoll.getRollType())
        {
            case STRIKE:
            {
                if (isLastRound)
                {
                    addNextScoresIntoFrame(scoreIterator, frameBuilder);
                    break;
                }
                else
                    secondRoll = new Roll("0", ScoreType.NONE);
                break;
            }
            default:
            {
                addNextScoresIntoFrame(scoreIterator, frameBuilder);
                break;
            }
        }

        if (!isLastRound)
            addFrameType(frameBuilder);

        return frameBuilder.build();
    }

    private void addNextScoresIntoFrame(Iterator<String> scoreIterator, Frame.Builder frame) {

        String secondRollScore = scoreIterator.next();
        String extraRollScore;
        Roll extraRoll = null;

        secondRoll = rollScore.getRoll(secondRollScore, rollScore.isFoulRoll(firstRoll.getScore()), rollScore.isSpareRoll(firstRoll.getScore(), secondRollScore));

        if (frameRound == 10)
        {
            if (firstRoll.getRollType() == ScoreType.STRIKE || secondRoll.getRollType() == ScoreType.SPARE)
            {
                extraRollScore = scoreIterator.next();
                extraRoll = rollScore.getRoll(extraRollScore, rollScore.isFoulRoll(secondRoll.getScore()), rollScore.isSpareRoll(secondRoll.getScore(), extraRollScore));

            }
            else
                extraRoll = new Roll("0", ScoreType.NONE);

        }
        frame.secondRoll(secondRoll).thirdRoll(extraRoll);
    }

    private void addFrameType(Frame.Builder builder) {

        ScoreType frameScoreType;

        ScoreType firstRollType = builder.getFirstRoll().getRollType();
        ScoreType secondRollType = builder.getSecondRoll() == null ? null : builder.getSecondRoll().getRollType();

        frameScoreType = getScoreType(firstRollType, secondRollType);

        builder.frameScoreType(frameScoreType);
    }

    private ScoreType getScoreType(ScoreType firstRollType, ScoreType secondRollType) {

        ScoreType frameScoreType;

        if (firstRollType == ScoreType.STRIKE)
            frameScoreType = ScoreType.STRIKE;
        else if (secondRollType == ScoreType.SPARE)
            frameScoreType = ScoreType.SPARE;
        else
            frameScoreType = ScoreType.NORMAL;

        return  frameScoreType;
    }

    private boolean isLastRound(){
        return frameRound == SystemConstant.LAST_ROUND_ID.getValue();
    }
}


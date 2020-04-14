package com.services;

import com.Component.RollScore;
import com.Enums.SystemConstant;
import com.interfaces.ScoreFrameService;
import com.Enums.ScoreType;
import com.models.Frame;
import com.models.Roll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class ScoreFrame implements ScoreFrameService
{
    private Roll firstRoll;
    private Roll secondRoll;

    @Autowired
    private RollScore rollScore;

    @Override
    public ArrayList<Frame> getFrames(ArrayList<String> bowlingGameInformationByPlayerName) {

        ArrayList<Frame> frames = new ArrayList<>();

        Iterator<String> scoreIterator = bowlingGameInformationByPlayerName.iterator();
        int frameRound = 1;

        do
        {
            Frame.Builder frameBuilder = new Frame.Builder();

            frameBuilder.round(frameRound);

            String firstRollScore = scoreIterator.next();

            firstRoll = rollScore.getRoll(firstRollScore, false, false);

            frameBuilder =  buildFrameBuilder(scoreIterator, frameBuilder);

            frames.add(frameBuilder.build());
            frameRound++;

        }while (scoreIterator.hasNext());

        return  frames;
    }

    @Override
    public Frame.Builder buildFrameBuilder(Iterator<String> scoreIterator, Frame.Builder frameBuilder) {

        frameBuilder.firstRoll(firstRoll).build();

        addNextScoresIntoFrame(scoreIterator, frameBuilder);

        addFrameType(frameBuilder);

        return frameBuilder;
    }

    private void addNextScoresIntoFrame(Iterator<String> scoreIterator, Frame.Builder frameBuilder) {

        String secondRollScore;
        String extraRollScore;
        Roll extraRoll;

        if (frameBuilder.getFirstRoll().getRollType() != ScoreType.STRIKE)
        {
            secondRollScore = scoreIterator.next();
            secondRoll =  getRollFrame(secondRollScore, firstRoll.getScore());
            extraRoll = new Roll("0", ScoreType.NONE);
        }
        else
        {
            secondRoll = new Roll("0", ScoreType.NONE);
            extraRoll = new Roll("0", ScoreType.NONE);
        }

        if (isLastRound(frameBuilder.getRound()))
        {
            secondRollScore = scoreIterator.next();
            secondRoll =  getRollFrame(secondRollScore, firstRoll.getScore());
            if (firstRoll.getRollType() == ScoreType.STRIKE || secondRoll.getRollType() == ScoreType.SPARE)
            {
                extraRollScore = scoreIterator.next();
                extraRoll = getRollFrame(extraRollScore, secondRollScore);

            }
            else
                extraRoll = new Roll("0", ScoreType.NONE);
        }

        frameBuilder.secondRoll(secondRoll).thirdRoll(extraRoll);
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

    private boolean isLastRound(int frameRound){
        return frameRound == SystemConstant.LAST_ROUND_ID.getValue();
    }

    private Roll getRollFrame(String currentRollScore, String previousRollScore){
        return rollScore.getRoll(currentRollScore, rollScore.isFoulRoll(previousRollScore), rollScore.isSpareRoll(previousRollScore, currentRollScore));
    }
}


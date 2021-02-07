package com.bowling.tenpinbowling.services;

import com.bowling.tenpinbowling.enums.ScoreType;
import com.bowling.tenpinbowling.enums.SystemConstant;
import com.bowling.tenpinbowling.interfaces.ScoreFrameCreatorService;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Roll;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Build Frames base score.
 */
@Service
public class ScoreFrameCreator implements ScoreFrameCreatorService {

    @Override
    public List<Frame> getScoreFrames(List<String> bowlingGameInformationByPlayerName) {

        List<Frame> frames = new ArrayList<>();

        Iterator<String> scoreIterator = bowlingGameInformationByPlayerName.iterator();
        int frameRound = 1;

        while (frameRound <= SystemConstant.LAST_ROUND_ID.getValue() && scoreIterator.hasNext()) {
            Frame.Builder frameBuilder = new Frame.Builder();
            frameBuilder.round(frameRound);

            buildFrameScoreBuilder(scoreIterator, frameBuilder);
            frames.add(frameBuilder.build());
            frameRound++;

        }
        return frames;
    }

    @Override
    public void buildFrameScoreBuilder(Iterator<String> scoreIterator, Frame.Builder frameBuilder) {

        String firstRollScore = scoreIterator.next();
        Roll firstRoll = buildRollFrame(firstRollScore, "0");

        frameBuilder.firstRoll(firstRoll).build();
        addNextScoresIntoFrame(scoreIterator, frameBuilder);

        addFrameType(frameBuilder);

    }

    @Override
    public Roll buildRollFrame(String scoreToEval, String previousRollScore) {

        ScoreType scoreType = getRollType(scoreToEval, isFoulRoll(scoreToEval), isSpareRoll(previousRollScore, scoreToEval));
        return new Roll(scoreToEval, scoreType);
    }

    private void addNextScoresIntoFrame(Iterator<String> scoreIterator, Frame.Builder frameBuilder) {

        String secondRollScore;
        String extraRollScore;
        Roll extraRoll;

        Roll secondRoll = new Roll("0", ScoreType.NONE);
        extraRoll = new Roll("0", ScoreType.NONE);

        if (!isStrikeRoll(frameBuilder.getFirstRoll().getScore()) || isLastRound(frameBuilder.getRound())) {
            if (scoreIterator.hasNext()) {
                secondRollScore = scoreIterator.next();
                secondRoll = buildRollFrame(secondRollScore, frameBuilder.getFirstRoll().getScore());

                if (isLastRound(frameBuilder.getRound())) {
                    if (isStrikeRoll(frameBuilder.getFirstRoll().getScore()) ||
                            (isSpareRoll(frameBuilder.getFirstRoll().getScore(), secondRollScore) || isStrikeRoll(secondRollScore))) {
                        extraRollScore = scoreIterator.next();
                        extraRoll = buildRollFrame(extraRollScore, secondRollScore);
                    }
                }
            }
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

        if (firstRollType == ScoreType.STRIKE) {
            frameScoreType = ScoreType.STRIKE;
        } else if (secondRollType == ScoreType.SPARE) {
            frameScoreType = ScoreType.SPARE;
        } else {
            frameScoreType = ScoreType.NORMAL;
        }

        return frameScoreType;
    }

    private boolean isLastRound(int frameRound) {
        return frameRound == SystemConstant.LAST_ROUND_ID.getValue();
    }

    private ScoreType getRollType(String rollScore, boolean isPreviousRollFoul, boolean isSpareRoll) {

        ScoreType scoreType;

        if (isFoulRoll(rollScore)) {
            scoreType = ScoreType.FOUL;
        } else if (noPingKnocked(rollScore)) {
            scoreType = ScoreType.NO_PINGS_KNOCKED;
        } else if (isStrikeRoll(rollScore) || isSpareRoll) {
            scoreType = (isPreviousRollFoul || isSpareRoll) ? ScoreType.SPARE : ScoreType.STRIKE;
        } else {
            scoreType = ScoreType.NORMAL;
        }

        return scoreType;
    }

    private boolean isStrikeRoll(String rollScore) {
        return "10".equals(rollScore);
    }

    private boolean isSpareRoll(String firstRollScore, String secondRollScore) {

        if (isNormalRoll(firstRollScore) && isNormalRoll(secondRollScore)) {
            return Integer.parseInt(firstRollScore) + Integer.parseInt(secondRollScore) == 10;
        }

        return isFoulRoll(firstRollScore) && isStrikeRoll(secondRollScore);

    }

    private boolean isNormalRoll(String rollScore) {
        return rollScore.matches("[0-9]");
    }

    private boolean isFoulRoll(String rollScore) {
        return "f".equals(rollScore.toLowerCase());
    }

    private boolean noPingKnocked(String rollScore) {
        return "0".equals(rollScore.toLowerCase());
    }
}


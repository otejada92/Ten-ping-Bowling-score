package com.bowling.tenpinbowling.services;

import com.bowling.tenpinbowling.enums.SystemConstant;
import com.bowling.tenpinbowling.interfaces.ScoreDataValidatorService;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import com.bowling.tenpinbowling.models.Roll;
import com.bowling.tenpinbowling.scoreprocessors.common.ScoreParseData;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class ScoreDataValidator implements ScoreDataValidatorService {

    private final ScoreParseData scoreParseData;

    public ScoreDataValidator() {
        scoreParseData = new ScoreParseData();
    }

    @Override
    public boolean validateScore(Map<Player, List<Frame>> score) {
        boolean isValid = true;

        for (Player player : score.keySet()){
            List<Frame> frameScores = score.get(player);

            if (existAllFrames(frameScores)) {
                for(Frame frame : frameScores) {

                    Roll firstRoll = frame.getFirstRoll();
                    Roll secondRoll = frame.getSecondRoll();

                    if(frame.getRound() == SystemConstant.LAST_ROUND_ID.getValue()) {
                        Roll thirdRoll = frame.getThirdRoll();
                        isValid = validateScore(firstRoll.getScore(), secondRoll.getScore(), thirdRoll.getScore());
                    }
                    else {
                        isValid = validateScore(firstRoll.getScore(), secondRoll.getScore());
                    }

                    if (!isValid) {
                        break;
                    }
                }

                if (!isValid) {
                    break;
                }
            }
            else {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    @Override
    public boolean existAllFrames(List<Frame> frameScore) {
        return frameScore.size() == 10;
    }

    @Override
    public Predicate<String> greaterThanTen() {
        return (String score) -> scoreParseData.parseRollScoreToInteger(score) > 10;
    }

    @Override
    public Predicate<String> lessThanZero() {
        return (String score) -> scoreParseData.parseRollScoreToInteger(score) < 0;
    }

    private int getScoreSum(String... scores) {

        return Arrays.stream(scores)
                .mapToInt(scoreParseData::parseRollScoreToInteger)
                .sum();
    }

    private boolean validateScore(String firstRoll, String secondRoll) {

        String invalidScorePattern = "([F f]|[0-9]+)";

        if (!firstRoll.matches(invalidScorePattern) || !secondRoll.matches(invalidScorePattern)){
            return false;
        }
        if(greaterThanTen().or(lessThanZero()).test(firstRoll)) {
            return false;
        }
        else if (greaterThanTen().or(lessThanZero()).test(secondRoll)) {
            return false;
        }
        else return getScoreSum(firstRoll, secondRoll) <= 10
                    && getScoreSum(firstRoll, secondRoll) >= 0;
    }

    private boolean validateScore(String firstRoll, String secondRoll, String thirdRoll) {
        if(greaterThanTen().or(lessThanZero()).test(firstRoll)) {
            return false;
        }
        else if (greaterThanTen().or(lessThanZero()).test(secondRoll)) {
            return  false;
        }
        else return getScoreSum(firstRoll, secondRoll, thirdRoll) <= 30
                    && getScoreSum(firstRoll, secondRoll, secondRoll, thirdRoll) >= 0;
    }
}
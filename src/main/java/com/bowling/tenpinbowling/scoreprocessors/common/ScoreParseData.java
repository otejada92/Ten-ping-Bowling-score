package com.bowling.tenpinbowling.scoreprocessors.common;


public class ScoreParseData {

    public int parseRollScoreToInteger(String score) {

        return parseRollScore(getEscapedRollScore(score));
    }

    private int parseRollScore(String value) {
        return Integer.parseInt(value);
    }

    private String getEscapedRollScore(String rollScore) {

        if (rollScore != null) {
            if (rollScore.matches(".*[f F].*"))
                rollScore = "0";
            if (rollScore.matches(".*[x X /].*"))
                rollScore = "10";
        }
        return rollScore;
    }
}

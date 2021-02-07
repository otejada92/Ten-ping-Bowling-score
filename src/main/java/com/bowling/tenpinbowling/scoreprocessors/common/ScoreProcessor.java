package com.bowling.tenpinbowling.scoreprocessors.common;

import com.bowling.tenpinbowling.interfaces.ProcessorStrategy;

public abstract class ScoreProcessor implements ProcessorStrategy {

    private final ScoreParseData scoreParse;

    public ScoreProcessor() {
        scoreParse = new ScoreParseData();
    }

    protected int parseRollScoreToInteger(String score) {

        return scoreParse.parseRollScoreToInteger(score);
    }

}

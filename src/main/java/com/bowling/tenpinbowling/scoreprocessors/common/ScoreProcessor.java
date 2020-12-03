package com.bowling.tenpinbowling.scoreprocessors.common;

import com.bowling.tenpinbowling.interfaces.ProcessorStrategy;

public abstract class ScoreProcessor implements ProcessorStrategy {

    private final ScoreParse scoreParse;

    public ScoreProcessor() {
        scoreParse = new ScoreParse();
    }

    protected int parseRollScoreToInteger(String score) {

        return scoreParse.parseRollScoreToInteger(score);
    }

}

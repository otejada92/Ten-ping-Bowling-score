package com.bowling.tenpinbowling.scoreprocessors;

import com.bowling.tenpinbowling.interfaces.ProcessorStrategy;

abstract class ScoreProcessor implements ProcessorStrategy {

    private ScoreParse scoreParse;

    ScoreProcessor(){
        scoreParse = new ScoreParse();
    }

    int parseRollScoreToInteger(String score){

        return scoreParse.parseRollScoreToInteger(score);
    }

}

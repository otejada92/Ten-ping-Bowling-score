package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Roll;

import java.util.ArrayList;
import java.util.Iterator;

public interface ScoreFrameCreatorService {

    ArrayList<Frame> getScoreFrames(ArrayList<String> bowlingGameInformationByPlayerName);
    void buildFrameScoreBuilder(Iterator<String> scoreIterator, Frame.Builder frameBuilder);
    Roll buildRollFrame(String scoreToEval, String previousRollScore);

}

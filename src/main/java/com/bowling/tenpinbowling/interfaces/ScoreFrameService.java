package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Roll;

import java.util.ArrayList;
import java.util.Iterator;

public interface ScoreFrameService {

    ArrayList<Frame> getScoreFrames(ArrayList<String> bowlingGameInformationByPlayerName);
    Frame.Builder getFrameScoreBuilder(Iterator<String> scoreIterator, Frame.Builder frameBuilder);
    Roll buildRollFrame(String scoreToEval, String previousRollScore);

}

package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Roll;

import java.util.Iterator;
import java.util.List;

public interface ScoreFrameCreatorService {

    List<Frame> getScoreFrames(List<String> bowlingGameInformationByPlayerName);

    void buildFrameScoreBuilder(Iterator<String> scoreIterator, Frame.Builder frameBuilder);

    Roll buildRollFrame(String scoreToEval, String previousRollScore);

}

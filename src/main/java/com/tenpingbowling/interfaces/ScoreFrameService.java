package com.tenpingbowling.interfaces;

import com.tenpingbowling.models.Frame;

import java.util.ArrayList;
import java.util.Iterator;

public interface ScoreFrameService {

    ArrayList<Frame> getFrames(ArrayList<String> bowlingGameInformationByPlayerName);
    Frame.Builder buildFrameBuilder(Iterator<String> scoreIterator, Frame.Builder frameBuilder);

}
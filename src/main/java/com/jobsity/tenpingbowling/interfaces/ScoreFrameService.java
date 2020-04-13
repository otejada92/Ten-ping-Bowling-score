package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Frame;

import java.util.ArrayList;
import java.util.Iterator;

public interface ScoreFrameService {

    ArrayList<Frame> getFrames(ArrayList<String> bowlingGameInformationByPlayerName);
    Frame.Builder buildFrameBuilder(Iterator<String> scoreIterator, Frame.Builder frameBuilder);

}

package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Frame;

import java.util.ArrayList;
import java.util.Iterator;

public interface FrameScoreServices {

    ArrayList<Frame> buildFrames(ArrayList<String> bowlingGameInformationByPlayerName);
    Frame.Builder buildCommonFrameBuilder(Iterator<String> scoreIterator);
    Frame.Builder build10thFrameBuilder(Iterator<String> scoreIterator);
}

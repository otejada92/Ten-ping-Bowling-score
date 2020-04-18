package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public interface ScoreFrameProcessorService {

    Map<Player, ArrayList<Frame>> calculateFrameScore(Map<Player, ArrayList<Frame>> scoreMap);
}

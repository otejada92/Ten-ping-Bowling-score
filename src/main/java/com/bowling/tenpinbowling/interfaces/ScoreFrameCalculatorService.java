package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public interface ScoreFrameCalculatorService {

    Map<Player, ArrayList<Frame>> calculateFrameScore(File bowlingFrameScore);
}

package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.util.List;
import java.util.Map;

public interface ScoreFrameProcessorService {

    Map<Player, List<Frame>> calculateFrameScore(Map<Player, List<Frame>> scores);
}

package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.util.List;
import java.util.Map;

public interface BowlingBoardService {

    void viewBowlingBoardResult(Map<Player, List<Frame>> frameScoreResult);
}

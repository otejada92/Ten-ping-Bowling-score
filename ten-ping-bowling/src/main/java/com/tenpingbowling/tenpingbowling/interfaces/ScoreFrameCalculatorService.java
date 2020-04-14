package com.tenpingbowling.tenpingbowling.interfaces;

import com.tenpingbowling.tenpingbowling.models.Frame;
import com.tenpingbowling.tenpingbowling.models.Player;

import java.util.ArrayList;
import java.util.Map;

public interface ScoreFrameCalculatorService {

    Map<Player, ArrayList<Frame>> calculateFrameScore();
}

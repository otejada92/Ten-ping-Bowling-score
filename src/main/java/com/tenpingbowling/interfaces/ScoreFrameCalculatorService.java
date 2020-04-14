package com.tenpingbowling.interfaces;

import com.tenpingbowling.models.Frame;
import com.tenpingbowling.models.Player;

import java.util.ArrayList;
import java.util.Map;

public interface ScoreFrameCalculatorService {

    Map<Player, ArrayList<Frame>> calculateFrameScore();
}

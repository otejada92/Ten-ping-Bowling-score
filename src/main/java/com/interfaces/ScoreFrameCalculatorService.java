package com.interfaces;

import com.models.Frame;
import com.models.Player;

import java.util.ArrayList;
import java.util.Map;

public interface ScoreFrameCalculatorService {

    Map<Player, ArrayList<Frame>> calculateFrameScore();
}

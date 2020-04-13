package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Player;

import java.util.ArrayList;
import java.util.Map;

public interface ScoreFrameCalculatorService {

    Map<Player, ArrayList<Frame>> calculateFrameScore();
}

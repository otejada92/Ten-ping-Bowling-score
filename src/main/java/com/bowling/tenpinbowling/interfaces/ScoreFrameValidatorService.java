package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.util.List;
import java.util.Map;

public interface ScoreFrameValidatorService {

    boolean validateFrameScores(Map<Player, List<String>> score);

    boolean expected();

    boolean greater();

    boolean less();

}

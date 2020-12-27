package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.util.List;
import java.util.Map;

public interface ScoreDataValidatorService {

    boolean validateScore(Map<Player, List<Frame>> score);

    boolean expected();

    boolean greater(List<String> scores);

    boolean less(List<String> scores);

}

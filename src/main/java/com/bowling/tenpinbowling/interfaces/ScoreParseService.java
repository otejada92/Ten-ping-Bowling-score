package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface ScoreParseService {
    Map<Player, List<String>> buildScoreMap(File bowlingFrameScore);

    Set<Player> retrievePlayers(List<String> scoreLine);

    List<String> readBowlingGameScore(File bowlingFrameScore) throws IOException;
}

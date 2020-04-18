package com.bowling.tenpinbowling.interfaces;


import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public interface ScoreMapService
{
    Map<Player, ArrayList<Frame>> buildScoreMap(File bowlingFrameScore);
    Set<Player> getPlayers(ArrayList<String> scoreLine);
    ArrayList<Frame> getScoreFrameByPlayer(Player player, ArrayList<String> scoreFilteredByPlayers);
    ArrayList<String> parseBowlingGameInfo(File bowlingFrameScore) throws IOException;
}

package com.bowling.tenpinbowling.interfaces;


import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public interface ScoreParseService
{
    Map<Player, ArrayList<Frame>> buildScoreMap(File bowlingFrameScore);
    Set<Player> retrievePlayers(ArrayList<String> scoreLine);
    ArrayList<Frame> retrieveScorePlayer(Player player, ArrayList<String> scoreFilteredByPlayers);
    ArrayList<String> parseBowlingGameInfo(File bowlingFrameScore) throws IOException;
}

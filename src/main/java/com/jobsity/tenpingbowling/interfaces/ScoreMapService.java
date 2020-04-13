package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface ScoreMapService
{
    Map<Player, ArrayList<Frame>> buildScoreMap();
    void addPlayers(ArrayList<String> scoreLine);
    void addPlayerScores(ArrayList<String> bowlingGameInformation, Set<Player> players);

}

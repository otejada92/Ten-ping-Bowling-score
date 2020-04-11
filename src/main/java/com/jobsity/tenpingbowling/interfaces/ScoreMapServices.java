package com.jobsity.tenpingbowling.interfaces;

import com.jobsity.tenpingbowling.models.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public interface ScoreMapServices
{
    public void buildScoreMap(File file);
    public void addPlayers(ArrayList<String> scoreLine);
    public  void addPlayerScores(ArrayList<String> bowlingGameInformation, Set<Player> players);

}

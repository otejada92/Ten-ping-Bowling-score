package com.bowling.tenpinbowling.services;

import com.bowling.tenpinbowling.Enums.SystemConstant;
import com.bowling.tenpinbowling.interfaces.ScoreFrameCreatorService;
import com.bowling.tenpinbowling.interfaces.ScoreParseService;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Parse the scores into a Map<{@link Player}, ArrayList<{@link Frame}>>.
*/
@Service
public class ScoreParse implements ScoreParseService {

    @Autowired
    private ScoreFrameCreatorService scoreFrameCreatorService;

    @Override
    public Map<Player, ArrayList<Frame>> buildScoreMap(File bowlingGameInfo) {

        Map<Player, ArrayList<Frame>>  scoreMap = new HashMap<>();
        ArrayList<String> bowlingGameInformation;

        try
        {
            bowlingGameInformation = parseBowlingGameInfo(bowlingGameInfo);

            Set<Player> players = retrievePlayers(bowlingGameInformation);

            for (Player player : players)
            {
                ArrayList<String> scoreGameFilteredByPlayer = getBowlingGameInfoFilteredByPlayer(bowlingGameInformation, player);

                ArrayList<Frame> playerScore = retrieveScorePlayer(player, scoreGameFilteredByPlayer);

                scoreMap.put(player, playerScore);
            }

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return  scoreMap;
    }

    @Override
    public ArrayList<String> parseBowlingGameInfo(File bowlingGameInfo) throws IOException {

        ArrayList<String> bowlingGameInformation = new ArrayList<>();
        BufferedReader bufferedReader;

        bufferedReader = new BufferedReader(new FileReader(bowlingGameInfo));

        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            bowlingGameInformation.add(line);
        }

        return bowlingGameInformation;
    }

    @Override
    public Set<Player> retrievePlayers(ArrayList<String> bowlingGameInformation) {

        Player player;
        Set<Player> players = new HashSet<>();
        Iterator<String> scoreIterator  = bowlingGameInformation.iterator();
        int maxIteration = 3;
        do
        {

            String[] scoreLine = splitScoreLine(scoreIterator.next());

            if (isValidScoreLine(scoreLine))
            {
                String playerName = getPlayerNameFromLine(scoreLine);

                if (isValidPlayerName(playerName))
                {
                    player = new Player();

                    player.setName(playerName);

                    players.add(player);
                }
            }
            else
            {
                System.out.println("Invalid score line found");
                break;
            }
            maxIteration--;
        }while (scoreIterator.hasNext() && maxIteration > 0);

        return  players;

    }

    @Override
    public ArrayList<Frame> retrieveScorePlayer(Player player, ArrayList<String> scoreFilteredByPlayer) {
        return scoreFrameCreatorService.getScoreFrames(scoreFilteredByPlayer);
    }

    private String[] splitScoreLine(String scoreLine){
        return scoreLine.split("\\s");
    }

    private  boolean isValidScoreLine(String[] scoreLine){
        return scoreLine.length ==  SystemConstant.VALID_SCORE_LINE_LENGTH.getValue();
    }

    private String getPlayerNameFromLine(String[] scoreLine){
        return scoreLine[SystemConstant.PLAYER_INDEX.getValue()];
    }

    private String getPlayerNameFromLine(String scoreLine){
        return getPlayerNameFromLine(splitScoreLine(scoreLine));
    }

    private String getScoreFromLine(String scoreLine){
        return splitScoreLine(scoreLine)[SystemConstant.SCORE_INDEX.getValue()];
    }

    private boolean isValidPlayerName(String playerName){
        return !StringUtils.isEmpty(playerName) && !playerName.matches(".*\\d.*");
    }

    private ArrayList<String> getBowlingGameInfoFilteredByPlayer(ArrayList<String> bowlingGameInformation, Player player){

       return bowlingGameInformation
                .stream()
                .filter(filterScoreByPlayerName(player.getName()))
                .map(this::getScoreFromLine)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    private Predicate<String> filterScoreByPlayerName(String playerNameFromMap){
        return playerName -> getPlayerNameFromLine(playerName).equals(playerNameFromMap);
    }


}

package com.jobsity.tenpingbowling.services;

import com.jobsity.tenpingbowling.Enums.RollType;
import com.jobsity.tenpingbowling.Enums.Validations;
import com.jobsity.tenpingbowling.interfaces.ScoreMapServices;
import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Player;
import com.jobsity.tenpingbowling.models.Roll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ScoreMapServicesImp implements ScoreMapServices {

    @Autowired
    FrameScoreServices frameScoreServices;

    private Map<Player, ArrayList<Frame>> scoreMap = new HashMap<Player, ArrayList<Frame>>();



    public void buildScoreMap(File file) {

        ArrayList<String> bowlingGameInformation = new ArrayList<String>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                bowlingGameInformation.add(line);
            }

            addPlayers(bowlingGameInformation);
            addPlayerScores(bowlingGameInformation, scoreMap.keySet());

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void addPlayers(ArrayList<String> bowlingGameInformation) {

        Player player;
        Iterator<String> scoreIterator  = bowlingGameInformation.iterator();
        do {

            String[] scoreLine = splitScoreLine(scoreIterator.next());

            if (isValidScoreLine(scoreLine))
            {
                String playerName = getPlayerNameFromLine(scoreLine);

                if (isValidPlayerName(playerName))
                {
                    player = new Player();

                    player.setName(playerName);

                    scoreMap.putIfAbsent(player, null);

                    if (hasMapValidKeySize(scoreMap.keySet()))
                        break;
                }
            }
            else
            {
                System.out.println("Invalid score line found");
                break;
            }
        }while (scoreIterator.hasNext());

    }

    @Override
    public void addPlayerScores(ArrayList<String> bowlingGameInformation, Set<Player> players) {

        ArrayList<String> scoreFilteredByPlayer;

        if (hasMapValidKeySize(players))
        {
            for (Player player : players)
            {
                scoreFilteredByPlayer = bowlingGameInformation
                        .stream()
                        .filter(filterScoreByPlayerName(player.getName()))
                        .map(this::getScoreFromLine)
                        .collect(Collectors.toCollection(ArrayList::new));

                ArrayList<Frame> frames = organizeFrameScore(scoreFilteredByPlayer);

                scoreMap.put(player, frames);
            }

        }

    }

    private ArrayList<Frame> organizeFrameScore(ArrayList<String> bowlingGameInformationByPlayerName){

        int frameRound = 1;
        ArrayList<Frame> frames = new ArrayList<>();

        Iterator<String> scoreIterator = bowlingGameInformationByPlayerName.iterator();
        do {

            String firstRollScoreFromFrame =  scoreIterator.next();
            Frame frame;
            Frame.Builder frameBuilder = null;
            Roll firstRoll;
            Roll secondRoll;

            if (frameRound < 10)
            {
                if (firstRollScoreFromFrame.equals("10"))
                {
                    frameBuilder = new Frame.Builder().round(frameRound)
                            .rollOne( new Roll(firstRollScoreFromFrame, RollType.STRIKE))
                            .rollTwo(new Roll("0", RollType.NONE));

                } else if (firstRollScoreFromFrame.toLowerCase().equals("f") || firstRollScoreFromFrame.toLowerCase().equals("0"))
                {
                    String secondRollScoreFromFrame = scoreIterator.next();
                    firstRoll = new Roll(firstRollScoreFromFrame, RollType.FOUL);

                    if (secondRollScoreFromFrame.equals("10"))
                    {
                        secondRoll = new Roll(secondRollScoreFromFrame, RollType.STRIKE);
                    }
                    else if (secondRollScoreFromFrame.toLowerCase().equals("f") || secondRollScoreFromFrame.toLowerCase().equals("0"))
                    {
                        secondRoll = new Roll(secondRollScoreFromFrame, RollType.FOUL);
                    }
                    else
                    {
                        secondRoll = new Roll(secondRollScoreFromFrame, RollType.NORMAL);
                    }

                    frameBuilder = new Frame.Builder().round(frameRound).rollOne(firstRoll).rollTwo(secondRoll);
                }
                else
                {
                    firstRoll = new Roll(firstRollScoreFromFrame, RollType.NORMAL);
                    String secondRollScoreFromFrame = scoreIterator.next();

                    if (secondRollScoreFromFrame.toLowerCase().equals("f") || secondRollScoreFromFrame.toLowerCase().equals("0"))
                    {
                        secondRoll = new Roll(secondRollScoreFromFrame, RollType.FOUL);
                    }
                    else if (Integer.parseInt(firstRollScoreFromFrame) + Integer.parseInt(secondRollScoreFromFrame) == 10)
                    {
                        secondRoll = new Roll(secondRollScoreFromFrame, RollType.SPARE);
                    }
                    else
                    {
                        secondRoll = new Roll(secondRollScoreFromFrame, RollType.NORMAL);
                    }

                    frameBuilder = new Frame.Builder().round(frameRound).rollOne(firstRoll).rollTwo(secondRoll);
                }
            }
            else
            {
                if (firstRollScoreFromFrame.equals("10"))
                {

                    frameBuilder = new Frame.Builder();

                    frameBuilder.rollOne(new Roll(firstRollScoreFromFrame, RollType.STRIKE));

                    String secondRollScoreFromFrame = scoreIterator.next();

                    if(secondRollScoreFromFrame.equals("10"))
                    {

                        frameBuilder.rollTwo(new Roll(secondRollScoreFromFrame, RollType.STRIKE));

                        String extraRollValue = scoreIterator.next();

                        if(extraRollValue.equals("10"))
                        {
                            frameBuilder.rollTwo(new Roll(secondRollScoreFromFrame, RollType.STRIKE)).rollThree(new Roll(secondRollScoreFromFrame, RollType.STRIKE));
                        }
                        else if (extraRollValue.toLowerCase().equals("f") || extraRollValue.toLowerCase().equals("0"))
                        {
                            frameBuilder.rollTwo( new Roll(extraRollValue, RollType.FOUL));
                        }
                        else if (Integer.parseInt(firstRollScoreFromFrame) + Integer.parseInt(secondRollScoreFromFrame) == 10)
                        {
                            frameBuilder.rollTwo( new Roll(extraRollValue, RollType.SPARE));
                        }
                        else
                        {
                            frameBuilder.rollTwo( new Roll(extraRollValue, RollType.NORMAL));
                        }
                    }
                    else if(secondRollScoreFromFrame.toLowerCase().equals("f") || secondRollScoreFromFrame.toLowerCase().equals("0"))
                    {
                        frameBuilder.rollTwo( new Roll(secondRollScoreFromFrame, RollType.FOUL));
                    }
                    else if (Integer.parseInt(firstRollScoreFromFrame) + Integer.parseInt(secondRollScoreFromFrame) == 10)
                    {
                        frameBuilder.rollTwo( new Roll(secondRollScoreFromFrame, RollType.SPARE));

                        String extraRollValue = scoreIterator.next();

                        if(extraRollValue.equals("10"))
                        {
                            frameBuilder.rollTwo(new Roll(secondRollScoreFromFrame, RollType.STRIKE)).rollThree(new Roll(secondRollScoreFromFrame, RollType.STRIKE));
                        }
                        else if (extraRollValue.toLowerCase().equals("f") || extraRollValue.toLowerCase().equals("0"))
                        {
                            frameBuilder.rollTwo( new Roll(extraRollValue, RollType.FOUL));
                        }
                        else
                        {
                            frameBuilder.rollTwo( new Roll(extraRollValue, RollType.NORMAL));
                        }

                    }
                    else
                    {
                        frameBuilder.rollTwo(new Roll(secondRollScoreFromFrame, RollType.NORMAL));
                    }

                }
            }

            frame = frameBuilder.build();

            frames.add(frame);
            frameRound++;

        }while (scoreIterator.hasNext());


        return  frames;
    }

    private String[] splitScoreLine(String line){
        return line.split("\\s");
    }

    private  boolean isValidScoreLine(String[] scoreLine){
        return scoreLine.length ==  Validations.VALID_SCORE_LINE_LENGTH.getValue();
    }

    private String getPlayerNameFromLine(String[] scoreLine){
        return scoreLine[Validations.PLAYER_INDEX.getValue()];
    }

    private String getPlayerNameFromLine(String scoreLine){
        return getPlayerNameFromLine(splitScoreLine(scoreLine));
    }

    private String getScoreFromLine(String scoreLine){
        return splitScoreLine(scoreLine)[Validations.SCORE_INDEX.getValue()];
    }

    private boolean isValidPlayerName(String name){
        return !StringUtils.isEmpty(name) && !name.matches(".*\\d.*");
    }

    private boolean hasMapValidKeySize(Set<Player> players){
        return players.size() == Validations.VALID_KEY_MAP_SIZE.getValue();
    }

    private Predicate<String> filterScoreByPlayerName(String playerNameFromMap){
        return playerName -> getPlayerNameFromLine(playerName).equals(playerNameFromMap);
    }

}

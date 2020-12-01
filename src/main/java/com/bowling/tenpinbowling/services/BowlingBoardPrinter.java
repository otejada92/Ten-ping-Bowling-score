package com.bowling.tenpinbowling.services;

import com.bowling.tenpinbowling.enums.SystemConstant;
import com.bowling.tenpinbowling.interfaces.BowlingBoardService;
import com.bowling.tenpinbowling.interfaces.ScoreFrameProcessorService;
import com.bowling.tenpinbowling.interfaces.ScoreParseService;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import com.bowling.tenpinbowling.models.Roll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Prints bowling score result
 */
@Service
public class BowlingBoardPrinter implements BowlingBoardService {

    @Autowired
    ScoreFrameProcessorService scoreFrameProcessorService;

    @Autowired
    ScoreParseService scoreParseService;

    private String format = "%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s \n";

    @Value("${file.path}") // delete this
    private String filePath;

    @Override
    public void viewBowlingBoardResult() {

        try
        {
            File file = new File(filePath);

            Map<Player, List<Frame>> scoreMap = scoreParseService.buildScoreMap(file);

            Map<Player, List<Frame>> frameScoreResult = scoreFrameProcessorService.calculateFrameScore(scoreMap);

            for (Player player : frameScoreResult.keySet()) {
                List<Frame> framesInfo = frameScoreResult.get(player);

                showFrameRoundLane(framesInfo);
                showPlayerLaneInfo(player);
                showPinFallsLane(framesInfo);
                showScoreLane(framesInfo);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); // remove this
        }
    }

    private void showFrameRoundLane(List<Frame> framesInfo) {

        String frameRound = framesInfo.stream().map(Frame::getRound)
                .map(Objects::toString)
                .collect(Collectors.joining(" "));

        System.out.print("Frame: ");
        System.out.printf(format, (Object[]) frameRound.split(" "));
    }

    private void showPlayerLaneInfo(Player player) {
        System.out.println(player.getName());
    }

    private void showPinFallsLane(List<Frame> framesInfo) {

        String pinFallsLaneValue = framesInfo.stream().map(this::getPinFallInfo)
                .map(Objects::toString).collect(Collectors.joining(" "));

        System.out.print("Pin falls: ");
        System.out.printf(format, (Object[]) pinFallsLaneValue.split(","));
    }

    private String getPinFallInfo(Frame frame){

        String value;
        if (frame.getRound() != SystemConstant.LAST_ROUND_ID.getValue())
        {
            value = parseRollScoreForBoard(frame.getFirstRoll(), frame.getSecondRoll());
        }
        else
        {
            value = parseRollScoreForBoard(frame.getFirstRoll(), frame.getSecondRoll(), frame.getThirdRoll());
        }

        return value;

    }

    private void showScoreLane(List<Frame> framesInfo) {

        String scores = framesInfo.stream().map(Frame::getFrameFinalScore).map(Objects::toString).collect(Collectors.joining(" "));
        System.out.print("Score: ");
        System.out.format(format, (Object[]) scores.split(" "));
    }

    private String parseRollScoreForBoard(Roll... rollsArray)
    {
        StringJoiner value = new StringJoiner(" ");
        List<Roll> rolls = new ArrayList<>(Arrays.asList(rollsArray));

        for (Roll roll : rolls)
        {
            switch (roll.getRollType())
            {
                case STRIKE:
                    value.add("X");
                    break;
                case SPARE:
                    value.add("/");
                    break;
                case FOUL:
                    value.add("F");
                    break;
                case NORMAL:
                    value.add(roll.getScore());
                    break;
                case NO_PINGS_KNOCKED:
                    value.add("0");
                    break;
                default:
                    break;
            }
        }
        value.add(",");
        return value.toString();
    }
}

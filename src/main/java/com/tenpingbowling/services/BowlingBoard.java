package com.tenpingbowling.services;

import com.tenpingbowling.Component.RollScore;
import com.tenpingbowling.Component.ScoreParse;
import com.tenpingbowling.Enums.SystemConstant;
import com.tenpingbowling.interfaces.BowlingBoardService;
import com.tenpingbowling.interfaces.ScoreFrameCalculatorService;
import com.tenpingbowling.models.Frame;
import com.tenpingbowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BowlingBoard implements BowlingBoardService {

    @Autowired
    private ScoreFrameCalculatorService scoreFrameCalculatorService;

    private Map<Player, ArrayList<Frame>> calculatedFrameMap;

    String format = "%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s \n";

    @Autowired
    private ScoreParse scoreParse;


    @Autowired
    private RollScore rollScore;

    @Override
    public void viewBowlingBoardResult() {

        calculatedFrameMap = scoreFrameCalculatorService.calculateFrameScore();

        for (Player player: calculatedFrameMap.keySet())
        {
            ArrayList<Frame> framesInfo = calculatedFrameMap.get(player);

            showFrameRoundLane(framesInfo);
            showPlayerLaneInfo(player);
            showPinFallsLane(framesInfo);
            showScoreLane(framesInfo);
        }
    }


    private void showFrameRoundLane(ArrayList<Frame> framesInfo) {

        String frameRound = framesInfo.stream().map(Frame::getRound)
                .map(Objects::toString)
                .collect(Collectors.joining(" "));

        System.out.print("Frame: ");
        System.out.printf(format, (Object[]) frameRound.split(" "));
    }

    private void showPlayerLaneInfo(Player player) {
        System.out.println(player.getName());
    }

    private void showPinFallsLane(ArrayList<Frame> framesInfo) {

        String pinFallsLaneValue = framesInfo.stream().map(this::getPinFallInfo)
                .map(Objects::toString).collect(Collectors.joining(" "));

        System.out.print("Pinfalls: ");
        System.out.printf(format, (Object[]) pinFallsLaneValue.split(","));
    }

    private String getPinFallInfo(Frame frame){

        String value;
        if (frame.getRound() != SystemConstant.LAST_ROUND_ID.getValue())
        {
            value =scoreParse.parseRollScoreForBoard(frame.getFirstRoll(), frame.getSecondRoll());
        }
        else
        {
            value = scoreParse.parseRollScoreForBoard(frame.getFirstRoll(), frame.getSecondRoll(), frame.getThirdRoll());
        }

        return value;

    }

    private void showScoreLane(ArrayList<Frame> framesInfo) {

        String scores = framesInfo.stream().map(Frame::getFrameFinalScore).map(Objects::toString).collect(Collectors.joining(" "));
        System.out.print("Score: ");
        System.out.format(format, (Object[]) scores.split(" "));
    }

    public void setCalculatedFrameMap(Map<Player, ArrayList<Frame>> calculatedFrameMap) {
        this.calculatedFrameMap = calculatedFrameMap;
    }
}

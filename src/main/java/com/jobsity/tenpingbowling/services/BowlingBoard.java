package com.jobsity.tenpingbowling.services;

import com.jobsity.tenpingbowling.Component.RollScore;
import com.jobsity.tenpingbowling.Component.ScoreParse;
import com.jobsity.tenpingbowling.Enums.SystemConstant;
import com.jobsity.tenpingbowling.interfaces.BowlingBoardService;
import com.jobsity.tenpingbowling.interfaces.ScoreFrameCalculatorService;
import com.jobsity.tenpingbowling.models.Frame;
import com.jobsity.tenpingbowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BowlingBoard implements BowlingBoardService {

    private ScoreFrameCalculatorService scoreFrameCalculatorService;

    private Map<Player, ArrayList<Frame>> calculatedFrameMap;

    String format = "%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s \n";

    @Autowired
    private ScoreParse scoreParse;


    @Autowired
    private RollScore rollScore;

    @Override
    public void viewBowlingBoardResult() {

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

    @Autowired
    public void setCalculatedFrameMap(ScoreFrameCalculator frameScoreCalculatorService) {
        this.calculatedFrameMap = frameScoreCalculatorService.calculateFrameScore();
    }
}

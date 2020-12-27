package com.bowling.tenpinbowling.services;

import com.bowling.tenpinbowling.enums.SystemConstant;
import com.bowling.tenpinbowling.interfaces.BowlingBoardService;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import com.bowling.tenpinbowling.models.Roll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Prints bowling score result
 */
@Service
public class BowlingBoardPrinter implements BowlingBoardService {

    private static final Logger logger = LogManager.getLogger(BowlingBoardPrinter.class);
    private final String LOG_FORMAT = "{} {} {} {} {} {} {} {} {} {} \n";

    @Override
    public void viewBowlingBoardResult(Map<Player, List<Frame>> frameResult) {

        for (Player player : frameResult.keySet()) {
            List<Frame> framesInfo = frameResult.get(player);

            showPinFallsLane(player, framesInfo);
            showScoreLane(player, framesInfo);
        }
    }

    private void showPinFallsLane(Player player, List<Frame> framesInfo) {

        String pinFallsLaneValue = framesInfo.stream().map(this::getPinFallInfo)
                .map(Objects::toString).collect(Collectors.joining(" "));

        logger.info(player.getName() + " - Pin falls: ");
        logger.info(LOG_FORMAT, (Object[]) pinFallsLaneValue.split(","));
    }

    private String getPinFallInfo(Frame frame) {

        if (frame.getRound() != SystemConstant.LAST_ROUND_ID.getValue()) {
            return parseRollScoreForBoard(frame.getFirstRoll(), frame.getSecondRoll());
        }
        return parseRollScoreForBoard(frame.getFirstRoll(), frame.getSecondRoll(), frame.getThirdRoll());

    }

    private void showScoreLane(Player player, List<Frame> framesInfo) {

        String scores = framesInfo.stream().map(Frame::getFrameFinalScore).map(Objects::toString).collect(Collectors.joining(" "));
        logger.info(player.getName() + " - score: ");
        logger.info(LOG_FORMAT, (Object[]) scores.split(" "));
    }

    private String parseRollScoreForBoard(Roll... rollsArray) {
        StringJoiner value = new StringJoiner(" ");
        List<Roll> rolls = new ArrayList<>(Arrays.asList(rollsArray));

        for (Roll roll : rolls) {
            switch (roll.getRollType()) {
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

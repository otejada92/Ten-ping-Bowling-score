package com.bowling.tenpinbowling.interfaces;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface ScoreDataValidatorService {

    boolean validateScore(Map<Player, List<Frame>> score);

    boolean existAllFrames(List<Frame> frameScore);

    Predicate<String> greaterThanTen();

    Predicate<String> lessThanZero();

}

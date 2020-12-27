package com.bowling.tenpinbowling.services;

import com.bowling.tenpinbowling.interfaces.ScoreDataValidatorService;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


// If there is Less than the expected throws the system crash.
// Allows consecutive values greater than 10(9,9)

@Service
public class ScoreDataValidator implements ScoreDataValidatorService {

    @Override
    public boolean validateScore(Map<Player, List<Frame>> score) {
        boolean isValid = true;

//        for (Player player : score.keySet()){
//            List<String> scores = score.get(player);
//            if (notEmptyLine(scores)) {
//
//            }
//        }

        return isValid;
    }

    // Verify - Allow more than the expected throws
    @Override
    public boolean expected() {

        return false;
    }

    //Verify - Allows values greater than 10
    @Override
    public boolean greater(List<String> scores) {
        return false;
//        return scores.stream().anyMatch(scores > 10);
    }

    //Verify - Allows values lower than 0
    @Override
    public boolean less(List<String> scores) {
        return false;
//        return scores.stream().anyMatch(scores < 0);
    }

    private boolean notEmptyLine(List<String> scores){
        return scores.stream().anyMatch(String::isEmpty);
    }
}

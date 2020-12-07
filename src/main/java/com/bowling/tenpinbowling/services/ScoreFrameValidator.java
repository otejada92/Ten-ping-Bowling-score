package com.bowling.tenpinbowling.services;

import com.bowling.tenpinbowling.interfaces.ScoreFrameValidatorService;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


// If there is Less than the expected throws the system crash.
// Allows consecutive values greater than 10(9,9)

@Service
public class ScoreFrameValidator implements ScoreFrameValidatorService {

    @Override
    public boolean validateFrameScores(Map<Player, List<Frame>> score) {


        return false;
    }

    // Verify - Allow more than the expected throws
    @Override
    public boolean expected() {


        return false;
    }

    //Verify - Allows values greater than 10
    @Override
    public boolean greater() {
        return false;
    }

    //Verify - Allows values lower than 0
    @Override
    public boolean less() {
        return false;
    }
}

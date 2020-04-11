package com.jobsity.tenpingbowling.services;


import com.jobsity.tenpingbowling.interfaces.ScoreMapServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class BowlingScoresServices {

    @Autowired
    private ScoreMapServices mapScoreServices;

    public void calculateScore(File file){
        mapScoreServices.buildScoreMap(file);
    }

}

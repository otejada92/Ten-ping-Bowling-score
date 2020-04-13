package com.jobsity.tenpingbowling;

import com.jobsity.tenpingbowling.interfaces.BowlingBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Initializer implements ApplicationRunner {

    @Autowired
    private BowlingBoardService bowlingBoardService;

    public static void main(String[] args) {
        SpringApplication.run(Initializer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        if (args.containsOption("file"))
        {
            bowlingBoardService.viewBowlingBoardResult();
        }
    }
}

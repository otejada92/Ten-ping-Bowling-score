package com.bowling.tenpinbowling;

import com.bowling.tenpinbowling.interfaces.*;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@PropertySource(value = "classpath:application-prod.properties")
public class Initializer implements ApplicationRunner {

    @Autowired
    private BowlingBoardService bowlingBoardService;

    @Autowired
    private ScoreParseService scoreParseService;

    @Autowired
    private ScoreFrameProcessorService scoreFrameProcessorService;

    @Autowired
    private ScoreFrameValidatorService scoreFrameValidatorService;

    @Autowired
    private ScoreFrameCreatorService scoreFrameCreatorService;

    private static final Logger logger = LogManager.getLogger(Initializer.class);

    public static void main(String[] args) {
        SpringApplication.run(Initializer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        if (!existFile(args)) {
            logger.error("Please set --file argument and provide file path.");
        } else {
            String filePath = args.getOptionValues("file").get(0);
            File file = new File(filePath);

            Map<Player, List<String>> scores = scoreParseService.buildScoreMap(file);

            if (!scoreFrameValidatorService.validateFrameScores(scores)) {
                logger.error("Score information is not valid.");
            } else {
                scoreFrameCreatorService.getScoreFrames();
//                Map<Player, List<Frame>> frameResult = scoreFrameProcessorService.calculateFrameScore(scores);
//                bowlingBoardService.viewBowlingBoardResult(frameResult);
            }
        }
    }

    private boolean existFile(ApplicationArguments args) {
        return args.containsOption("file") &&
                !StringUtils.isEmpty(args.getOptionValues("file").get(0));
    }
}

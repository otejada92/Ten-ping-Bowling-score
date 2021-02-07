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
import java.util.HashMap;
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
    private ScoreDataValidatorService scoreFrameValidatorService;

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
            Map<Player, List<Frame>> playerFrames = new HashMap<>();

            String filePath = args.getOptionValues("file").get(0);
            File file = new File(filePath);
            if(file.exists()) {
                Map<Player, List<String>> scores = scoreParseService.buildScoreMap(file);
                for (Player player : scores.keySet()){
                    List<String> playerScore = scores.get(player);
                    List<Frame> frames = scoreFrameCreatorService.getScoreFrames(playerScore);
                    playerFrames.put(player, frames);
                }

                if (!scoreFrameValidatorService.validateScore(playerFrames)) {
                    logger.error("Score information is not valid.");
                } else {

                    Map<Player, List<Frame>> frameResult = scoreFrameProcessorService.calculateFrameScore(playerFrames);
                    bowlingBoardService.viewBowlingBoardResult(frameResult);
                }
            } else  {
                logger.error("File does not exist, {}", filePath);
            }
        }
    }

    private boolean existFile(ApplicationArguments args) {
        return args.containsOption("file") &&
                !StringUtils.isEmpty(args.getOptionValues("file").get(0));
    }
}

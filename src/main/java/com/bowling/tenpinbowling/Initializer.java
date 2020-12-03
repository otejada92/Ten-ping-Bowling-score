package com.bowling.tenpinbowling;

import com.bowling.tenpinbowling.interfaces.BowlingBoardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

@SpringBootApplication
@PropertySource(value = "classpath:application-prod.properties")
public class Initializer implements ApplicationRunner {
//
//    @Autowired
//    private BowlingBoardService bowlingBoardService;

    private static final Logger logger = LogManager.getLogger(Initializer.class);

    public static void main(String[] args) {
        SpringApplication.run(Initializer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        if ((args.containsOption("file") && !StringUtils.isEmpty(args.getOptionValues("file").get(0)))) {
//            bowlingBoardService.viewBowlingBoardResult();
        } else {
            logger.error("Please set --file argument and provide file path.");
        }

    }
}

package com.tenpingbowling.tenpingbowling;

import com.tenpingbowling.tenpingbowling.interfaces.BowlingBoardService;
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

    @Autowired
    private BowlingBoardService bowlingBoardService;

    public static void main(String[] args) {
        SpringApplication.run(Initializer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        if ((args.containsOption("file") && !StringUtils.isEmpty(args.getOptionValues("file").get(0))))
        {
            bowlingBoardService.viewBowlingBoardResult();
        }
        else
        {
            System.err.println("ERROR: Please set --file argument and provide file path.");
        }
    }
}

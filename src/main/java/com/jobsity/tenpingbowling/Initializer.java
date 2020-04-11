package com.jobsity.tenpingbowling;

import com.jobsity.tenpingbowling.interfaces.ScoreMapServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class Initializer implements CommandLineRunner {

    @Autowired
    private ScoreMapServices mapScoreServices;

    public static void main(String[] args) {
        SpringApplication.run(Initializer.class, args);
    }

    public void run(String... args) throws Exception {

        if (args.length > 0){
            File file = new File(args[0]);
            mapScoreServices.buildScoreMap(file);
        }
    }
}

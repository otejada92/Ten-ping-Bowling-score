package com.jobsity.tenpingbowling.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;

@Component
public class BowlingScoreFile extends File {

    public BowlingScoreFile(@Value("${file}")String pathname) {
        super(pathname);
    }
}

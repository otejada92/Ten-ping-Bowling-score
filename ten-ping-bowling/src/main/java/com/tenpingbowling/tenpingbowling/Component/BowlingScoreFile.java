package com.tenpingbowling.tenpingbowling.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class BowlingScoreFile extends File {

    public BowlingScoreFile(@Value("${file.path}")String pathname) {
        super(pathname);
    }
}

package com.bowling.tenpinbowling.integrations;


import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import com.bowling.tenpinbowling.scoreprocessors.NormalScoreProcessor;
import com.bowling.tenpinbowling.scoreprocessors.SpareScoreProcessor;
import com.bowling.tenpinbowling.scoreprocessors.StrikeScoreProcessor;
import com.bowling.tenpinbowling.scoreprocessors.TenthFrameProcessor;
import com.bowling.tenpinbowling.services.ScoreFrameCreator;
import com.bowling.tenpinbowling.services.ScoreFrameProcessor;
import com.bowling.tenpinbowling.services.ScoreParse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.bowling.tenpinbowling.asserts.Assertions.assertThat;


@SpringBootTest(classes = {ScoreParse.class, ScoreFrameCreator.class, ScoreFrameProcessor.class, NormalScoreProcessor.class, SpareScoreProcessor.class, StrikeScoreProcessor.class, TenthFrameProcessor.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class ScoreFrameProcessorTest {

    @Autowired
    private ScoreParse scoreParse;

    @Autowired
    private ScoreFrameProcessor scoreFrameProcessor;

    private File normalScoreGameFile;
    private File perfectScoreGameFile;
    private File zeroScoreGameFile;


    @Before
    public  void setUp() throws FileNotFoundException {
        normalScoreGameFile = ResourceUtils.getFile("classpath:normal-score.txt");
        perfectScoreGameFile = ResourceUtils.getFile("classpath:perfect-score.txt");
        zeroScoreGameFile = ResourceUtils.getFile("classpath:zero-score.txt");
    }

//    @Test
//    public void calculateFrameScores_NormalGamePlay_AssertTrueNonError(){
//        assertFrameScoreProcessorResult(normalScoreGameFile, 167);
//    }
//
//    @Test
//    public void calculateFrameScores_PerfectScoreGamePlay_AssertTrueNonError(){
//        assertFrameScoreProcessorResult(perfectScoreGameFile, 300);
//    }
//
//    @Test
//    public void calculateFrameScores_ZeroScoreGamePlay_AssertTrueNonError(){
//        assertFrameScoreProcessorResult(zeroScoreGameFile, 0);
//    }


//    private void assertFrameScoreProcessorResult(File gameScoreFile, int expectedFinalScore)
//    {
//        Map<Player, List<>> gameScoreMap = retrieveFirstPlayer(buildScoreMap(gameScoreFile));
//
//        Player player = gameScoreMap.keySet().stream().findFirst().get();
//        assertThat(player).hasName("Carl");
//
//        List<Frame>  frameScore = gameScoreMap.get(player);
//        Assert.assertEquals(frameScore.size(), 10);
//
//        Map<Player, List<Frame>> calculatedFrameScore = scoreFrameProcessor.calculateFrameScore(gameScoreMap);
//
//
//        Frame lastFrame = calculatedFrameScore.get(player).stream().reduce((oldFrame, newFrame) -> newFrame).get();
//        assertThat(lastFrame).frameScoreEqual(expectedFinalScore);
//
//
//    }

    private Map<Player, List<String>> buildScoreMap(File gameScore){

        Map<Player, List<String>>  scoreMap = scoreParse.buildScoreMap(gameScore);
        assert scoreMap.size() >= 1 : "Scores were not parse correctly, scoreParse.buildScoreMap()";
        return scoreMap;
    }

    private Map<Player, List<Frame>> retrieveFirstPlayer(Map<Player, List<Frame>> gameScoreMap)
    {
        Map.Entry<Player, List<Frame>> firstPlayerInfo = gameScoreMap.entrySet()
                .stream()
                .findFirst()
                .get();

        return Collections.singletonMap(firstPlayerInfo.getKey(), firstPlayerInfo.getValue());
    }


}

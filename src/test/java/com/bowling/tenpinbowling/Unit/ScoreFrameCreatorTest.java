package com.bowling.tenpinbowling.Unit;

import com.bowling.tenpinbowling.Enums.ScoreType;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Roll;
import com.bowling.tenpinbowling.services.ScoreFrameCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;


@SpringBootTest(classes = {com.bowling.tenpinbowling.services.ScoreFrameCreator.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class ScoreFrameCreatorTest {


    @Value("${normal.score}")
    private ArrayList<String> filteredScore;

    @Value("${normal.frame.score.data}")
    private ArrayList<String> frameScore;

    @Autowired
    private ScoreFrameCreator scoreFrameCreator;


    @Test
    public void getScoreFrames_TenValidFrameScore_ReturnTrue(){

        ArrayList<Frame> frames = scoreFrameCreator.getScoreFrames(filteredScore);
        Assert.assertEquals(10, frames.size());
    }

    @Test
    public void getFrameScoreBuilder_NormalRollScores_AssertEqualTrue()
    {
        Frame.Builder frameBuilder = new Frame.Builder();
        scoreFrameCreator.buildFrameScoreBuilder(frameScore.iterator(),frameBuilder);
        Assert.assertNotNull(frameBuilder);
        Assert.assertEquals(ScoreType.NORMAL, frameBuilder.getFrameScoreType());
    }

    @Test
    public void buildRollFrame_StrikeScore_AssertEqualTrue(){

        Roll roll = getRoll("10", "0");
        Assert.assertEquals(ScoreType.STRIKE, roll.getRollType());
    }

    @Test
    public void buildRollFrame_SpareScore_AssertEqualTrue(){

        Roll roll = getRoll("3", "7");
        Assert.assertEquals(ScoreType.SPARE, roll.getRollType());
    }

    @Test
    public void buildRollFrame_NormalScore_AssertEqualTrue(){

        Roll roll = getRoll("2", "3");
        Assert.assertEquals(ScoreType.NORMAL, roll.getRollType());
    }

    @Test
    public void buildRollFrame_FoulScore_AssertEqualTrue(){

        Roll roll = getRoll("F", "10");
        Assert.assertEquals(ScoreType.FOUL, roll.getRollType());
    }

    @Test
    public void buildRollFrame_NoPingsKnocked_AssertEqualTrue(){

        Roll roll = getRoll("0", "10");
        Assert.assertEquals(ScoreType.NO_PINGS_KNOCKED, roll.getRollType());
    }

    private Roll getRoll(String scoreToEval, String previosScore)
    {
        return scoreFrameCreator.buildRollFrame(scoreToEval, previosScore);
    }


}

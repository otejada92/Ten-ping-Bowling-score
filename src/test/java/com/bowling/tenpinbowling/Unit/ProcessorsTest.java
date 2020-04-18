package com.bowling.tenpinbowling.Unit;


import com.bowling.tenpinbowling.scoreprocessors.NormalScoreProcessor;
import com.bowling.tenpinbowling.scoreprocessors.SpareScoreProcessor;
import com.bowling.tenpinbowling.scoreprocessors.StrikeScoreProcessor;
import com.bowling.tenpinbowling.scoreprocessors.TenthFrameProcessor;
import com.bowling.tenpinbowling.Enums.ScoreType;
import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Roll;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = {NormalScoreProcessor.class, SpareScoreProcessor.class, StrikeScoreProcessor.class, TenthFrameProcessor.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class ProcessorsTest {

    @Autowired
    private NormalScoreProcessor normalScoreProcessor;

    @Autowired
    private SpareScoreProcessor spareScoreProcessor;

    @Autowired
    private StrikeScoreProcessor strikeScoreProcessor;

    @Autowired
    private TenthFrameProcessor tenthFrameProcessor;

    private Frame normalScoreTypeFrame;
    private Frame foulScoreTypeFrame;
    private Frame strikeScoreTypeFrame;
    private Frame spareScoreTypeFrame;
    private Frame tenthScoreTypeFrame;

    @Before
    public void setUp()
    {
        normalScoreTypeFrame = new Frame.Builder().firstRoll(new Roll("6", ScoreType.NORMAL))
                .secondRoll(new Roll("3", ScoreType.NORMAL))
                .frameScoreType(ScoreType.NORMAL)
                .build();
        foulScoreTypeFrame = new Frame.Builder().firstRoll(new Roll("F", ScoreType.FOUL))
                .secondRoll(new Roll("3", ScoreType.NORMAL))
                .frameScoreType(ScoreType.NORMAL)
                .build();
        strikeScoreTypeFrame = new Frame.Builder().firstRoll(new Roll("10", ScoreType.STRIKE))
                .secondRoll(new Roll("0", ScoreType.NONE))
                .frameScoreType(ScoreType.STRIKE)
                .build();
        spareScoreTypeFrame = new Frame.Builder().firstRoll(new Roll("5", ScoreType.NORMAL))
                .secondRoll(new Roll("5", ScoreType.SPARE))
                .frameScoreType(ScoreType.SPARE)
                .build();
        tenthScoreTypeFrame = new Frame.Builder().firstRoll(new Roll("F", ScoreType.NORMAL))
                .secondRoll(new Roll("5", ScoreType.NORMAL))
                .thirdRoll(new Roll("0", ScoreType.STRIKE))
                .build();
    }

    @Test
    public void calculateScore_FrameWithScoreNormalType_AssertEqualTrue(){
        int result = normalScoreProcessor.calculateScore(normalScoreTypeFrame);
        Assert.assertEquals(9, result);
    }

    @Test
    public void calculateScore_FrameWithFoul_AssertEqualTrue(){
        int result = normalScoreProcessor.calculateScore(foulScoreTypeFrame);
        Assert.assertEquals(3, result);
    }

    @Test
    public void calculateScore_FrameWithScoreStrikeType_AssertEqualTrue(){
        int result = strikeScoreProcessor.calculateScore(strikeScoreTypeFrame, normalScoreTypeFrame);
        Assert.assertEquals(19, result);
    }

    @Test
    public void calculateScore_FrameWithScoreSpareType_AssertEqualTrue(){
        int result = spareScoreProcessor.calculateScore(spareScoreTypeFrame, normalScoreTypeFrame);
        Assert.assertEquals(16, result);

    }

    @Test
    public void calculateScore_TenthFrameWithStrike_AssertEqualTrue(){
        int result = tenthFrameProcessor.calculateScore(tenthScoreTypeFrame);
        Assert.assertEquals(5, result);
    }

}

package com.bowling.tenpinbowling.Unit;


import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;
import com.bowling.tenpinbowling.services.ScoreFrameImp;
import com.bowling.tenpinbowling.services.ScoreMapImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

@SpringBootTest(classes = {ScoreMapImp.class, ScoreFrameImp.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class ScoreMapTest {

    @Autowired
    private ScoreMapImp scoreMapServiceImp;

    private ArrayList<String> scoreLine;

    private URL normalScore;

    @Value("${normal.score}")
    private ArrayList<String> filteredScore;

    @Before
    public void setUp() throws IOException {

        URL normalScore = this.getClass().getResource("/normal-game.txt");
        scoreLine = scoreMapServiceImp.parseBowlingGameInfo(new File(normalScore.getFile()));
    }

    @Test
    public void getPlayers_NormalGameTwoPlayers_AssertEqualTrue() {

        Set<Player> players = getPlayerFromScoreLine();
        Assert.assertEquals(2, players.size());
    }

    @Test
    public void  getScoreFrame_NormalGameTwoPlayers_AssertEqualTrue() {

        Player player = getFirstPlayer();
        ArrayList<Frame> frames = scoreMapServiceImp.getScoreFrameByPlayer(player, filteredScore);
        Assert.assertEquals(10, frames.size());
    }

    private Set<Player> getPlayerFromScoreLine(){
        return scoreMapServiceImp.getPlayers(scoreLine);
    }

    private  Player getFirstPlayer()
    {
        Set<Player> players = getPlayerFromScoreLine();
        assert players.size() > 0 : "No players found, getFirstPlayer()";
        return players.stream().findFirst().get();
    }

}

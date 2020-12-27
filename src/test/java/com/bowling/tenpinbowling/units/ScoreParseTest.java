package com.bowling.tenpinbowling.units;


import com.bowling.tenpinbowling.models.Player;
import com.bowling.tenpinbowling.services.ScoreFrameCreator;
import com.bowling.tenpinbowling.services.ScoreParse;
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
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = {ScoreParse.class, ScoreFrameCreator.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class ScoreParseTest {

    @Autowired
    ScoreParse scoreParse;
    private List <String> scoreLine;

    @Value("${normal.score}")
    ArrayList<String> filteredScore;

    @Before
    public void setUp() throws IOException {

        URL normalScore = this.getClass().getResource("/normal-score.txt");
        scoreLine = scoreParse.readBowlingGameScore(new File(normalScore.getFile()));
    }

    @Test
    public void retrievePlayers_NormalGameTwoPlayers_AssertEqualTrue() {

        Set<Player> players = getPlayerFromScoreLine();
        Assert.assertEquals(2, players.size());
    }

    @Test
    public void  retrieveScorePlayer_NormalGameTwoPlayers_AssertEqualTrue() {

        Player player = getFirstPlayer();
//        List<Frame> frames = scoreParse.retrieveScorePlayer(player, filteredScore);
//        Assert.assertEquals(10, frames.size());
    }

    private Set<Player> getPlayerFromScoreLine(){
        return scoreParse.retrievePlayers(scoreLine);
    }

    private  Player getFirstPlayer()
    {
        Set<Player> players = getPlayerFromScoreLine();
        assert players.isEmpty() : "No players found, getFirstPlayer()";
        return players.stream().findFirst().get();
    }

}

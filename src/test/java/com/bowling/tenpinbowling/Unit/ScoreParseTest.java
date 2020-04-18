package com.bowling.tenpinbowling.Unit;


import com.bowling.tenpinbowling.models.Frame;
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
import java.util.Set;

@SpringBootTest(classes = {ScoreParse.class, ScoreFrameCreator.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class ScoreParseTest {

    @Autowired
    private ScoreParse scoreParse;

    private ArrayList<String> scoreLine;

    private URL normalScore;

    @Value("${normal.score}")
    private ArrayList<String> filteredScore;

    @Before
    public void setUp() throws IOException {

        URL normalScore = this.getClass().getResource("/normal-game.txt");
        scoreLine = scoreParse.parseBowlingGameInfo(new File(normalScore.getFile()));
    }

    @Test
    public void retrievePlayers_NormalGameTwoPlayers_AssertEqualTrue() {

        Set<Player> players = getPlayerFromScoreLine();
        Assert.assertEquals(2, players.size());
    }

    @Test
    public void  retrieveScorePlayer_NormalGameTwoPlayers_AssertEqualTrue() {

        Player player = getFirstPlayer();
        ArrayList<Frame> frames = scoreParse.retrieveScorePlayer(player, filteredScore);
        Assert.assertEquals(10, frames.size());
    }

    private Set<Player> getPlayerFromScoreLine(){
        return scoreParse.retrievePlayers(scoreLine);
    }

    private  Player getFirstPlayer()
    {
        Set<Player> players = getPlayerFromScoreLine();
        assert players.size() > 0 : "No players found, getFirstPlayer()";
        return players.stream().findFirst().get();
    }

}

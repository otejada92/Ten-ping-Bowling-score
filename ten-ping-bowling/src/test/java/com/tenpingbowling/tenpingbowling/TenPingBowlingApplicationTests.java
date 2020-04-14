package com.tenpingbowling.tenpingbowling;

import com.tenpingbowling.tenpingbowling.services.ScoreMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.ApplicationContextAssert;

@SpringBootTest
class TenPingBowlingApplicationTests {


	@Autowired
	ScoreMap scoreMap;

	@Test
	void contextLoads() {

		scoreMap.getScoreMap();
	}

}

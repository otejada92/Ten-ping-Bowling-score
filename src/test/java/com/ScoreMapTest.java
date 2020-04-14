package com;

import com.services.ScoreMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ScoreMapTest {


	@Autowired
	private ScoreMap scoreMapServiceImp;

	@Test
	public void getScoreMap_AssertTrue_IfTenFrameFound(){

		Map map = scoreMapServiceImp.getScoreMap();


	}
}

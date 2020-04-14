package com.tenpingbowling;

import com.tenpingbowling.services.ScoreMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DemoApplicationTests {

	@Autowired
	private ScoreMap scoreMapServiceImp;

	@Test
	public void getScoreMap_AssertTrue_IfTenFrameFound(){

		Map map = scoreMapServiceImp.getScoreMap();
//		Assert.assertNotEquals(map.keySet().size(), 1);

	}
}

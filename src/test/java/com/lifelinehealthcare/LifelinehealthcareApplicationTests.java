package com.lifelinehealthcare;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LifelinehealthcareApplicationTests {

	@Test
	public void applicationTest() {
		LifelinehealthcareApplication.main(new String[] {});
		assertTrue(true);
	}

}

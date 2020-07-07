package com.pk.publisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublisherServiceApplicationTest {

	public PublisherServiceApplicationTest() {
	}
	
	 
	  @Test
	  public  void applicationContextTest() {
	  PublisherServiceApplication.main(new String[] {});
	}

}

package com.aquariusmaster.elastic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = {ElasticfillerApplication.class, ApplicationConfig.class} )
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticfillerApplicationTests {

	@Autowired
	private ElasticsearchCrudRepository repository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void elasticsearchTest() {

		User user = new User();
		user.setId(1);
		user.setGender("Male");
		user.setFirst_name("John");
		user.setLast_name("Smith");
		user.setEmail("aq@ya.ru");
		user.setIp_address("192.168.1.1");

		repository.save(user);
	}

}

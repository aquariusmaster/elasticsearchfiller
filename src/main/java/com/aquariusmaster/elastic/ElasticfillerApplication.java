package com.aquariusmaster.elastic;

import org.elasticsearch.ElasticsearchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ElasticfillerApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticfillerApplication.class);

	/**
	 * counter is indicate number of already proceeded entries
	 */
	private static long counter;
	/**
	 * STEP is indicate number of entries to be parsed at ones
	 */
	private static final int STEP = 50;

	@Resource
	private Environment environment;

	@Autowired
	private ElasticsearchCrudRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ElasticfillerApplication.class, args);

	}

	@Scheduled(fixedDelay = 5000)
	public void scheduler(){

		LOGGER.info("Scheduler started");

		List<User> users = UserJSONParser.parse(STEP, counter, environment.getProperty("elasticsearch.file"));

		if (users.size() == 0){
			LOGGER.info("There is no data. Job done! Exiting.");
			System.exit(0);
		}
		LOGGER.info("Trying to save...");
		try {
			repository.save(users);
			counter+= STEP;
			LOGGER.info("Saved.");
		}catch (ElasticsearchException e){

			LOGGER.error("Error saving - ElasticsearchException: elasticsearch node is not ready. Waiting for next attempt. " + e.toString());
			try {
				LOGGER.info("Sleep");
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

	}

}

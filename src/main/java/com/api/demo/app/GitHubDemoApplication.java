package com.api.demo.app;

import com.app.model.EventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.app.*")
@SpringBootApplication
public class GitHubDemoApplication {

	private final Logger logger = LoggerFactory.getLogger(GitHubDemoApplication.class);

	@Bean
	public EventRequest getEventRequest(){
		return new EventRequest();
	}

	public static void main(String[] args) {
		SpringApplication.run(GitHubDemoApplication.class, args);
	}

}

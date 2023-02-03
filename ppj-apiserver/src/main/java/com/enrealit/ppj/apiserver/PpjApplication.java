package com.enrealit.ppj.apiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.enrealit.ppj" })
@EntityScan(basePackages = "com.enrealit.ppj.data")
@EnableMongoRepositories(basePackages = { "com.enrealit.ppj.data.repository" })
@EnableMongoAuditing
public class PpjApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpjApplication.class, args);
	}
}

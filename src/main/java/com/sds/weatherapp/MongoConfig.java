package com.sds.weatherapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
	
	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create("mongodb://weather:1234@localhost:27017");
	}
	
	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), "weather");
	}
}

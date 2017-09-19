

package com.test1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@PropertySource(value = { "classpath:mongo-sources.properties" })

public class MongoConfig {

	@Resource 
	Environment environment;
	
	public @Bean
	@Lazy
	MongoDbFactory mongoDbFactory() throws Exception {
		
		 MongoCredential credential = MongoCredential.createCredential((environment
					.getProperty("mongo.server.userName")), environment
					.getProperty("mongo.server.database"),environment
					.getProperty("mongo.server.password").toCharArray());
		 
		
		
		List<MongoCredential> mongoCredentials = new ArrayList<MongoCredential>();
		mongoCredentials.add(credential);
		 
		MongoClientOptions options = MongoClientOptions
				.builder()
				.connectionsPerHost(
						Integer.parseInt(environment
								.getProperty("mongo.server.connectionsPerHost")))
				.build();

		List<ServerAddress> serverAddress = Arrays
				.asList(new ServerAddress(environment
						.getProperty("mongo.server.ip"), Integer
						.valueOf(environment.getProperty("mongo.server.port"))));

		MongoClient mongoClient = new MongoClient(serverAddress,mongoCredentials,options);
		
		System.out.println(mongoClient);
		
		return new SimpleMongoDbFactory(mongoClient, 
				environment.getProperty("mongo.server.database"));
	}

	public @Bean
	@Lazy
	MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
	     return mongoTemplate;
	}

 

}


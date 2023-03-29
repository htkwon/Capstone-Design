package com.hansung.hansungcommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class HansungCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(HansungCommunityApplication.class, args);
	}

}

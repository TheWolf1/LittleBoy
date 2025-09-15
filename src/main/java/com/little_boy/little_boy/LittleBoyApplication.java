package com.little_boy.little_boy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LittleBoyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LittleBoyApplication.class, args);
	}

}

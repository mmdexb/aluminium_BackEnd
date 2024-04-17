package com.hyper.aluminium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//田鑫提交111
@SpringBootApplication
@EnableScheduling
public class AluminiumApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluminiumApplication.class, args);
	}

}

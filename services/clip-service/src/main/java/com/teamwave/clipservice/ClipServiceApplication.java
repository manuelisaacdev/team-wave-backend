package com.teamwave.clipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ClipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClipServiceApplication.class, args);
	}

}

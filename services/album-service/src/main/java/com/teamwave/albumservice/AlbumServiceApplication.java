package com.teamwave.albumservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AlbumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlbumServiceApplication.class, args);
	}

}

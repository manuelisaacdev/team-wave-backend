package com.teamwave.locationservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamwave.locationservice.model.Country;
import com.teamwave.locationservice.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class LocationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDB(CountryRepository countryRepository) {
		return args -> {
			if (countryRepository.count() > 0) return;
			System.out.println("===================> COUNTRIES: " + countryRepository.saveAll(new ObjectMapper().readValue(getClass().getResource("/json/Countries.json") , new TypeReference<>() {})).size());
		};
	}
}

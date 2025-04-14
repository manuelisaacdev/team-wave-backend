package com.teamwave.musicalgenresservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamwave.musicalgenresservice.model.MusicalGenre;
import com.teamwave.musicalgenresservice.repository.MusicalGenreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class MusicalGenresServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicalGenresServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(MusicalGenreRepository musicalGenreRepository) {
		return args -> {
			if (musicalGenreRepository.count() == 0) {
				List<MusicalGenre> musicalGenres = musicalGenreRepository.saveAll(new ObjectMapper().readValue(getClass().getResource("/json/MusicalGenres.json"), new TypeReference<>() {}));
				System.out.println("=======================> MUSICAL GENRES: " + musicalGenres.size());
			}
		};
	}

}

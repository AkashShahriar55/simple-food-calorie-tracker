package com.akash.caloriecounter;

import com.akash.caloriecounter.services.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CalorieCounterBackendApplication implements CommandLineRunner {

	@Resource
	FileStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(CalorieCounterBackendApplication.class, args);

	}


	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}

}

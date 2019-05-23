package com.codevert.tvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class TvpApplication {

	public static void main(String[] args) {
		SpringApplication.run(TvpApplication.class, args);
	}
}
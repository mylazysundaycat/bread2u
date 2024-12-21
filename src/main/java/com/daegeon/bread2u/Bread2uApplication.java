package com.daegeon.bread2u;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Bread2uApplication {
	public static void main(String[] args) {
		SpringApplication.run(Bread2uApplication.class, args);
	}

}

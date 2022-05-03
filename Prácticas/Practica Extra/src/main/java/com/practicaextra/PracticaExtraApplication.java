package com.practicaextra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PracticaExtraApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticaExtraApplication.class, args);
	}

}

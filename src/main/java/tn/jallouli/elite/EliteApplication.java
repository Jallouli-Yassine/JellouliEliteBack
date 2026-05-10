package tn.jallouli.elite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EliteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EliteApplication.class, args);
	}

}

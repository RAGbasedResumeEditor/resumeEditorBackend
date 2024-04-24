package com.team2.resumeeditorproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {"com.team2.resumeeditorproject.user","com.team2.resumeeditorproject.config"})
@EnableJpaRepositories("com.team2.resumeeditorproject.user.repository")
@EntityScan("com.team2.resumeeditorproject.user.domain")
public class ResumeEditorProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeEditorProjectApplication.class, args);
	}

}

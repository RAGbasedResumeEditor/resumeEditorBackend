package com.team2.resumeeditorproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {"com.team2.resumeeditorproject"})
//@SpringBootApplication
//@ComponentScan(basePackages = {"com.team2.resumeeditorproject.user","com.team2.resumeeditorproject.config", "com.team2.resumeeditorproject.resume"})
//@EnableJpaRepositories({"com.team2.resumeeditorproject.user.repository","com.team2.resumeeditorproject.resume.repository"})
//@EntityScan({"com.team2.resumeeditorproject.user.domain","com.team2.resumeeditorproject.user.domain"})
public class ResumeEditorProjectApplication {

	public static void main(String[] args) {
		String getVersion = org.springframework.core.SpringVersion.getVersion();

		System.out.println(getVersion);
		SpringApplication.run(ResumeEditorProjectApplication.class, args);
	}

}

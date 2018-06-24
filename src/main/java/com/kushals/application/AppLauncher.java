package com.kushals.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.kushals.*")
public class AppLauncher {
	public static void main(String[] args) {
		SpringApplication.run(AppLauncher.class, args);
	}
}

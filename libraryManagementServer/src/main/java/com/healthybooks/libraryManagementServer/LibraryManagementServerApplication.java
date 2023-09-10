package com.healthybooks.libraryManagementServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LibraryManagementServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementServerApplication.class, args);
	}

}

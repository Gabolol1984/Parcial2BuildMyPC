package com.buildmypc.msvc_build;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcBuildApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcBuildApplication.class, args);
	}

}

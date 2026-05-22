package com.buildmypc.msvc.cpu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CpuApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpuApplication.class, args);
	}

}

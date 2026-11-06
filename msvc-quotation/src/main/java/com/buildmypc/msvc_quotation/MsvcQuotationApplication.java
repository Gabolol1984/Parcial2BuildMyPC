package com.buildmypc.msvc_quotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient   // Registro en Eureka
@EnableFeignClients      // Llama a build-service y component-service
@EnableScheduling        // Activa la tarea programada de vencimiento
public class MsvcQuotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcQuotationApplication.class, args);
	}

}

package msvc.motherboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MotherboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotherboardApplication.class, args);
	}

}

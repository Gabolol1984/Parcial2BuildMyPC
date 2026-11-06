package msvc.msvc_ram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcRamApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcRamApplication.class, args);
	}

}

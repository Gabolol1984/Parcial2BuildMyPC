package msvc_component.msvc_component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcComponentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcComponentApplication.class, args);
	}

}

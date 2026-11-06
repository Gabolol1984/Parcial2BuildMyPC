package msvc.gpu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcGpuApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcGpuApplication.class, args);
	}

}

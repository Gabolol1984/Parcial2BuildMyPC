package msvc.powersupply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcPowerSupplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcPowerSupplyApplication.class, args);
	}

}

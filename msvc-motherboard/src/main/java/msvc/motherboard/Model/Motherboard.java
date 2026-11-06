package msvc.motherboard.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;


@Data
@Table(name = "motherboard")
@Entity
@SpringBootApplication
public class Motherboard {
    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String model;

    @NotBlank
    private String socket;

    @NotBlank
    private String ramType;

    private Integer ramSlots;

    @Bean
    public Object hateoasHalProviderDummy() {
        return new Object();
    }
}

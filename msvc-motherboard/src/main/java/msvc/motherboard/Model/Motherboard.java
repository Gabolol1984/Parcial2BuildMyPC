package msvc.motherboard.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Table(name = "motherboard")
@Entity
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
}

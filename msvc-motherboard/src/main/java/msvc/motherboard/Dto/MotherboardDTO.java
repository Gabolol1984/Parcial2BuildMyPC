package msvc.motherboard.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MotherboardDTO {
    @NotBlank
    private String model;

    @NotBlank
    private String socket;

    @NotBlank
    private String ramType;

    @NotNull
    private Integer ramSlots;
}

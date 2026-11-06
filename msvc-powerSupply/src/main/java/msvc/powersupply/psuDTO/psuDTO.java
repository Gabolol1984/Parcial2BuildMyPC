package msvc.powersupply.psuDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class psuDTO {
    @NotNull(message = "El componenteId es obligatorio")
    private Long componenteId;

    @NotNull(message = "La potencia es obligatoria")
    private Integer potenciaWatts;

    @NotBlank(message = "La certificación es obligatoria")
    @Size(max = 20, message = "La certificación no puede superar 20 caracteres")
    private String certificacion;

    @NotNull(message = "Debe indicar si la fuente es modular")
    private Boolean modular;

    @NotNull(message = "La cantidad de conectores PCIe es obligatoria")
    @Min(value = 0, message = "Los conectores PCIe no pueden ser negativos")
    private Integer conectoresPcie;
}

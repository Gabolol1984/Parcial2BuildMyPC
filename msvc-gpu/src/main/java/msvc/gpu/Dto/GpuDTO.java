package msvc.gpu.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GpuDTO {
    private Long id;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio base debe ser mayor que cero")
    private Double precioBase;

    @NotBlank(message = "El tipo de memoria es obligatorio")
    private String tipoMemoria;

    @NotNull(message = "La memoria en GB es obligatoria")
    @Positive(message = "La memoria en GB debe ser mayor que cero")
    private Integer memoriaGb;

    @NotNull(message = "El consumo en watts es obligatorio")
    @Positive(message = "El consumo en watts debe ser mayor que cero")
    private Integer consumoW;

    private String descripcion;
}
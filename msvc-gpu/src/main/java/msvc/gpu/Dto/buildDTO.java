package msvc.gpu.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class buildDTO {

    @NotNull(message = "El usuarioId es obligatorio")
    private Long Id;

    private Long cpuId;
    private Long gpuId;
    private Long motherboardId;
    private Long ramId;
    private Long fuenteId;
    private Long usuarioId;
}

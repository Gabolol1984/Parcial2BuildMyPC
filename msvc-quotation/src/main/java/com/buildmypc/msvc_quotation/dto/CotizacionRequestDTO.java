package com.buildmypc.msvc_quotation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CotizacionRequestDTO {
    @NotNull(message = "El buildId es obligatorio")
    private Long buildId;

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    @Min(value = 0, message = "El descuento no puede ser negativo")
    private Double descuento = 0.0;   // Opcional, por defecto 0
}

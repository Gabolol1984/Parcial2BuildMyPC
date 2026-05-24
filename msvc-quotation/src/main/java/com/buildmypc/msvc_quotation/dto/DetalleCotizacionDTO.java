package com.buildmypc.msvc_quotation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleCotizacionDTO {
    private Long id;
    private Long componenteId;
    private String nombre;
    private Double precio;
}

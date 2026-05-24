package com.buildmypc.msvc_quotation.dto;

import com.buildmypc.msvc_quotation.model.Cotizacion.EstadoCotizacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CotizacionResponseDTO {
    private Long id;
    private Long buildId;
    private Long usuarioId;
    private Double subtotal;
    private Double descuento;
    private Double total;
    private EstadoCotizacion estado;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaVencimiento;
    private List<DetalleCotizacionDTO> detalles;
}

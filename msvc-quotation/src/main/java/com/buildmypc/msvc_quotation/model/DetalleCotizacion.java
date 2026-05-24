package com.buildmypc.msvc_quotation.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_cotizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleCotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cotizacionId;

    @Column(nullable = false)
    private Long componenteId;

    // Guardamos nombre y precio al momento de cotizar
    // (si el precio cambia después, el historial queda intacto)
    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false)
    private Double precio;
}

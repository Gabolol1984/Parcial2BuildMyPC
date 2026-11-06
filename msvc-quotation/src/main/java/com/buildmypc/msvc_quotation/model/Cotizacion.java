package com.buildmypc.msvc_quotation.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cotizaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long buildId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double descuento;      // Monto fijo de descuento

    @Column(nullable = false)
    private Double total;          // subtotal - descuento

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCotizacion estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    private LocalDateTime fechaVencimiento;

    @PrePersist
    protected void onCreate() {
        this.fechaEmision    = LocalDateTime.now();
        this.estado          = EstadoCotizacion.PENDIENTE;
        this.fechaVencimiento = this.fechaEmision.plusDays(7);
    }

    public enum EstadoCotizacion {
        PENDIENTE,   // Emitida, esperando respuesta
        APROBADA,    // Usuario aprobó
        RECHAZADA,   // Usuario rechazó
        VENCIDA      // Expiró sin respuesta
    }
}

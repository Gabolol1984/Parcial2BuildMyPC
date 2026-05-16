package com.service.cpu.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cpus")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class cpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencia al catálogo general (component-service)
    @Column(nullable = false)
    private Long componenteId;

    @Column(nullable = false)
    private String socket;           // Ej: AM5, LGA1700

    @Column(nullable = false)
    private Integer nucleos;

    @Column(nullable = false)
    private Integer hilos;

    @Column(nullable = false)
    private Double frecuenciaBase;   // GHz

    private Double frecuenciaTurbo;  // GHz

    @Column(nullable = false)
    private Integer tdpWatts;        // Consumo energético

    @Column
    private String generacion;       // Ej: Ryzen 7000, Core 13th Gen

    private Boolean soportaDdr4;
    private Boolean soportaDdr5;

}

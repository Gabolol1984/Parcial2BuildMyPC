package com.service.cpu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name ="cpu_id")
    private Long id;

    // Referencia al catálogo general (component-service)
    @Column(nullable = false)
    private Long componenteId;

    @NotBlank(message = "El campo de socket no puede estar vacio")
    @Column(nullable = false)
    private String socket;           // Ej: AM5, LGA1700

    @NotBlank(message = "El campo de nucleos no puede estar vacio")
    @Column(nullable = false)
    private Integer nucleos;

    @NotBlank(message = "El campo de hilos no puede estar vacio")
    @Column(nullable = false)
    private Integer hilos;

    @NotBlank(message = "El campo de las frecuencias no puede estar vacio")
    @Column(nullable = false)
    private Double frecuenciaBase;   // GH

    @Column(nullable = false)
    private Integer tdpWatts;        // Consumo energético

    @NotBlank(message = "El campo de generacion de cpu no puede estar vacio")
    @Column
    private String generacion;       // Ej: Ryzen 7000, Core 13th Gen

    private Boolean soportaDdr4;
    private Boolean soportaDdr5;

}

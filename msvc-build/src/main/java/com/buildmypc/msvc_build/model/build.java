package com.buildmypc.msvc_build.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="builds")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    private Long cpuId;
    private Long gpuId;
    private Long motherboardId;
    private Long ramId;
    private Long fuenteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoBuild estado;

    public enum EstadoBuild {
        BORRADOR,       // Recién creada, sin todos los componentes
        COMPLETA,       // Todos los componentes seleccionados
        VALIDADA,       // Compatible según compatibility-service
        INCOMPATIBLE,   // No pasó validación de compatibilidad
        COTIZADA,       // Ya tiene cotización generada
        APROBADA        // El usuario aprobó la cotización
    }


}

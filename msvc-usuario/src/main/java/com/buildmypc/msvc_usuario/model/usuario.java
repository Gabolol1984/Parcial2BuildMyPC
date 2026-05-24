package com.buildmypc.msvc_usuario.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="usuario_id")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "el apellido es obligatorio")
    @Column(nullable = false, length = 80)
    private String apellido;

    @NotBlank(message = "el email es obligatorio")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(length = 20)
    private String telefono;

    //roles disponibles
    @NotNull(message = "el rol no es valido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolFuncional rolFuncional;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuario estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = EstadoUsuario.ACTIVO;
    }

    public enum RolFuncional {
        USUARIO, TECNICO, ADMINISTRADOR
    }

    public enum EstadoUsuario {
        ACTIVO, INACTIVO
    }
}

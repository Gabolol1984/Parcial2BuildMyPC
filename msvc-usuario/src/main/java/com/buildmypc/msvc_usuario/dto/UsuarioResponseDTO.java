package com.buildmypc.msvc_usuario.dto;

import com.buildmypc.msvc_usuario.model.Usuario.EstadoUsuario;
import com.buildmypc.msvc_usuario.model.Usuario.RolFuncional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private RolFuncional rolFuncional;
    private EstadoUsuario estado;
    private LocalDateTime fechaRegistro;
}

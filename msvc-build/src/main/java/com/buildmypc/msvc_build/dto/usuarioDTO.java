package com.buildmypc.msvc_build.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
public class usuarioDTO {
    private Long id;
    private String username;
    private Set<String> roles;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String rolFuncional;
}

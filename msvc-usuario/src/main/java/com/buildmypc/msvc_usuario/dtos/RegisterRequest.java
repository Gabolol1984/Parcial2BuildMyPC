package com.buildmypc.msvc_usuario.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// Datos para crear un usuario. 'roles' es opcional: si viene vacio se asigna ROLE_PACIENTE.
// Ejemplo: {"username":"dr.pc","password":"1234","roles":["ROLE_USUARIO"]}
@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Set<String> roles = new HashSet<>();
}

package com.buildmypc.msvc_usuario.controllers;

import com.buildmypc.msvc_usuario.dtos.UsuarioDTO;
import com.buildmypc.msvc_usuario.models.Rol;
import com.buildmypc.msvc_usuario.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// Endpoint PROTEGIDO: gestion de usuarios
@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios (requiere token)")
@SecurityRequirement(name = "bearer-jwt")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Solo ADMIN. Devuelve usuarios sin la contrasena.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> usuarios = this.usuarioRepository.findAll().stream()
                .map(u -> new UsuarioDTO(
                        u.getUsuarioId(),
                        u.getUsername(),
                        u.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())))
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Devuelve los datos basicos del usuario según su ID.")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        return this.usuarioRepository.findById(id)
                .map(u -> new UsuarioDTO(
                        u.getUsuarioId(),
                        u.getUsername(),
                        u.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

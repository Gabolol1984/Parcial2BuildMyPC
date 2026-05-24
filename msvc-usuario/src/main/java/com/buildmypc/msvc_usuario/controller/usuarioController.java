package com.buildmypc.msvc_usuario.controller;

import com.buildmypc.msvc_usuario.dto.UsuarioRequestDTO;
import com.buildmypc.msvc_usuario.dto.UsuarioResponseDTO;
import com.buildmypc.msvc_usuario.model.Usuario;
import com.buildmypc.msvc_usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Validated
@RequiredArgsConstructor
public class usuarioController {

    private final UsuarioService usuarioService;

    // POST /api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crear(dto));
    }

    // GET /api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // GET /api/usuarios/email?email=juan@mail.com
    // Usado por auth-service para validar que el usuario existe
    @GetMapping("/email")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }

    // GET /api/usuarios/rol?rol=TECNICO
    @GetMapping("/rol")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorRol(@RequestParam String rol) {
        return ResponseEntity.ok(usuarioService.listarPorRol(rol));
    }

    // GET /api/usuarios/estado?estado=ACTIVO
    @GetMapping("/estado")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorEstado(
            @RequestParam String estado) {
        return ResponseEntity.ok(usuarioService.listarPorEstado(estado));
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    // PATCH /api/usuarios/{id}/desactivar
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        usuarioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}

package com.buildmypc.msvc_usuario.controller;

import com.buildmypc.msvc_usuario.assemblers.UsuarioModelAssembler;
import com.buildmypc.msvc_usuario.dto.UsuarioRequestDTO;
import com.buildmypc.msvc_usuario.dto.UsuarioResponseDTO;
import com.buildmypc.msvc_usuario.model.Usuario;
import com.buildmypc.msvc_usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Validated
@Tag(name="Usuarios V1", description = "Metodos CRUD para la gestion de usuarios")
public class usuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    // POST /api/usuarios
    @PostMapping
    @Operation(summary = "Agregar un Usuario", description = "crea y Agrega un nuevo usuario")
    public ResponseEntity<UsuarioResponseDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crear(dto));
    }

    // GET /api/usuarios
    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Muestra todos los usuarios creados")
    @ApiResponse(responseCode = "200",description = "Operacion exitosa",
                content = @Content(mediaType = "application/json"))
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por id", description = "Muestra un usuario con el ID especifico")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @Parameter(description = "Id del usuario a buscar")
            @PathVariable Long id) {
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

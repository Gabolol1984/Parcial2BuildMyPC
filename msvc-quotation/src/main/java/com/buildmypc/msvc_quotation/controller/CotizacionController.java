package com.buildmypc.msvc_quotation.controller;

import com.buildmypc.msvc_quotation.dto.CotizacionRequestDTO;
import com.buildmypc.msvc_quotation.dto.CotizacionResponseDTO;
import com.buildmypc.msvc_quotation.service.CotizacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cotizaciones")
@RequiredArgsConstructor
public class CotizacionController {
    private final CotizacionService cotizacionService;

    // POST /api/cotizaciones
    // Genera cotización para una build VALIDADA
    @PostMapping
    public ResponseEntity<CotizacionResponseDTO> crear(
            @Valid @RequestBody CotizacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cotizacionService.crear(dto));
    }

    // GET /api/cotizaciones
    @GetMapping
    public ResponseEntity<List<CotizacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(cotizacionService.listarTodas());
    }

    // GET /api/cotizaciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CotizacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cotizacionService.buscarPorId(id));
    }

    // GET /api/cotizaciones/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CotizacionResponseDTO>> listarPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(cotizacionService.listarPorUsuario(usuarioId));
    }

    // GET /api/cotizaciones/estado?estado=PENDIENTE
    @GetMapping("/estado")
    public ResponseEntity<List<CotizacionResponseDTO>> listarPorEstado(
            @RequestParam String estado) {
        return ResponseEntity.ok(cotizacionService.listarPorEstado(estado));
    }

    // GET /api/cotizaciones/build/{buildId}
    // Obtiene la cotización de una build específica
    @GetMapping("/build/{buildId}")
    public ResponseEntity<CotizacionResponseDTO> buscarPorBuild(
            @PathVariable Long buildId) {
        return ResponseEntity.ok(cotizacionService.buscarPorBuild(buildId));
    }

    // PATCH /api/cotizaciones/{id}/aprobar
    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<CotizacionResponseDTO> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(cotizacionService.aprobar(id));
    }

    // PATCH /api/cotizaciones/{id}/rechazar
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<CotizacionResponseDTO> rechazar(@PathVariable Long id) {
        return ResponseEntity.ok(cotizacionService.rechazar(id));
    }
}

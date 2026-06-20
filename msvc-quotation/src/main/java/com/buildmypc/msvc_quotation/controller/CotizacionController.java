package com.buildmypc.msvc_quotation.controller;

import com.buildmypc.msvc_quotation.assemblers.QuotationModelAssembler;
import com.buildmypc.msvc_quotation.dto.CotizacionRequestDTO;
import com.buildmypc.msvc_quotation.dto.CotizacionResponseDTO;
import com.buildmypc.msvc_quotation.service.CotizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v1/cotizaciones")
@Validated
@Tag(name = "Cotizaciones V1", description = "Métodos para la gestión y consulta de cotizaciones de builds")
@RequiredArgsConstructor
public class CotizacionController {
    @Autowired
    private final CotizacionService cotizacionService;
    @Autowired
    private final QuotationModelAssembler quotationModelAssembler;

    // POST /api/cotizaciones
    // Genera cotización para una build VALIDADA
    @PostMapping
    @Operation(summary = "Genera una cotización", description = "Genera una nueva cotización para una build previamente validada")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para generar la cotización", required = true,
            content = @Content(schema = @Schema(implementation = CotizacionRequestDTO.class))
    )
    public ResponseEntity<CotizacionResponseDTO> crear(
            @Valid @RequestBody CotizacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cotizacionService.crear(dto));
    }

    // GET /api/cotizaciones
    @GetMapping
    @Operation(summary = "Listado de todas las cotizaciones", description = "Devuelve una lista con todas las cotizaciones registradas")
    @ApiResponse(responseCode = "200", description = "Operación Exitosa")
    public ResponseEntity<List<CotizacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(cotizacionService.listarTodas());
    }

    // GET /api/cotizaciones/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Búsqueda de una cotización por ID", description = "Devuelve una cotización detallada según su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cotización encontrada"),
            @ApiResponse(responseCode = "404", description = "La cotización no existe en el sistema")
    })
    public ResponseEntity<CotizacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cotizacionService.buscarPorId(id));
    }

    // GET /api/cotizaciones/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar por usuario", description = "Obtiene todas las cotizaciones asociadas a un usuario específico")
    public ResponseEntity<List<CotizacionResponseDTO>> listarPorUsuario(
            @Parameter(description = "ID del usuario", required = true, example = "10")
            @PathVariable Long usuarioId){
        return ResponseEntity.ok(cotizacionService.listarPorUsuario(usuarioId));
    }

    // GET /api/cotizaciones/estado?estado=PENDIENTE
    @GetMapping("/estado")
    @Operation(summary = "Filtrar por estado", description = "Devuelve un listado de cotizaciones filtradas por su estado (ej. PENDIENTE, APROBADA)")
    public ResponseEntity<List<CotizacionResponseDTO>> listarPorEstado(
            @Parameter(description = "Estado de la cotización", required = true, example = "PENDIENTE")
            @RequestParam String estado) {
        return ResponseEntity.ok(cotizacionService.listarPorEstado(estado));
    }

    // GET /api/cotizaciones/build/{buildId}
    // Obtiene la cotización de una build específica
    @GetMapping("/build/{buildId}")
    @Operation(summary = "Buscar por Build", description = "Obtiene la cotización asignada a una configuración/build específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cotización de la build encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe cotización para el ID de build enviado")
    })
    public ResponseEntity<CotizacionResponseDTO> buscarPorBuild(
            @Parameter(description = "ID de la Build", required = true, example = "5")
            @PathVariable Long buildId) {
        return ResponseEntity.ok(cotizacionService.buscarPorBuild(buildId));
    }

    // PATCH /api/cotizaciones/{id}/aprobar
    @PatchMapping("/{id}/aprobar")
    @Operation(summary = "Aprobar cotización", description = "Cambia el estado de una cotización de forma lógica a APROBADA")
    public ResponseEntity<CotizacionResponseDTO> aprobar(
            @Parameter(description = "Id de la cotización a aprobar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(cotizacionService.aprobar(id));
    }

    // PATCH /api/cotizaciones/{id}/rechazar
    @PatchMapping("/{id}/rechazar")
    @Operation(summary = "Rechazar cotización", description = "Cambia el estado de una cotización de forma lógica a RECHAZADA")
    public ResponseEntity<CotizacionResponseDTO> rechazar(
            @Parameter(description = "Id de la cotización a rechazar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(cotizacionService.rechazar(id));
    }
}

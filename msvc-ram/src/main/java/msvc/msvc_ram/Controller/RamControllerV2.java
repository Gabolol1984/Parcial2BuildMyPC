package msvc.msvc_ram.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import msvc.msvc_ram.Dto.RamDTO;
import msvc.msvc_ram.Model.Ram;
import msvc.msvc_ram.Service.RamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v2/ram")
@RequiredArgsConstructor
@Validated
@Tag(name="RamV2", description = "Metodos CRUD para la gestion de Ram")
public class RamControllerV2 {


    @Autowired  // O usa @RequiredArgsConstructor si usas Lombok
    private RamService service;

    @GetMapping
    @Operation(summary = "Listar todas las RAMs",
            description = "Muestra todas las RAMs creadas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json"))
    public ResponseEntity<List<RamDTO>> findAll() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar RAM por ID",
            description = "Muestra una RAM con el ID específico")
    public ResponseEntity<RamDTO> findById(
            @Parameter(description = "ID de la RAM a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/modelo/{modelo}")
    @Operation(summary = "Buscar RAM por modelo",
            description = "Muestra una RAM con el modelo específico")
    public ResponseEntity<RamDTO> findByModelo(
            @Parameter(description = "Modelo de la RAM a buscar")
            @PathVariable String modelo) {
        return ResponseEntity.ok(service.buscarPorModelo(modelo));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar RAMs por tipo",
            description = "Muestra todas las RAMs de un tipo específico")
    public ResponseEntity<List<RamDTO>> findByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar RAMs por estado",
            description = "Muestra todas las RAMs con un estado específico")
    public ResponseEntity<List<RamDTO>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @GetMapping("/marca/{marca}")
    @Operation(summary = "Listar RAMs por marca",
            description = "Muestra todas las RAMs de una marca específica")
    public ResponseEntity<List<RamDTO>> findByMarca(@PathVariable String marca) {
        return ResponseEntity.ok(service.listarPorMarca(marca));
    }

    @GetMapping("/ddr/{tipoDdr}")
    @Operation(summary = "Listar RAMs por tipo DDR",
            description = "Muestra todas las RAMs con un tipo DDR específico")
    public ResponseEntity<List<RamDTO>> findByTipoDdr(@PathVariable String tipoDdr) {
        return ResponseEntity.ok(service.listarPorTipoDdr(tipoDdr));
    }

    @GetMapping("/capacidad/{capacidadMinima}")
    @Operation(summary = "Listar RAMs por capacidad mínima",
            description = "Muestra todas las RAMs con capacidad mayor o igual a la especificada")
    public ResponseEntity<List<RamDTO>> findByCapacidadMinima(@PathVariable Integer capacidadMinima) {
        return ResponseEntity.ok(service.listarPorCapacidadMinima(capacidadMinima));
    }

    @PostMapping
    @Operation(summary = "Crear una RAM",
            description = "Crea y agrega una nueva RAM")
    @ApiResponse(responseCode = "201", description = "RAM creada exitosamente")
    public ResponseEntity<RamDTO> create(@Valid @RequestBody RamDTO ram) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(ram));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una RAM",
            description = "Actualiza una RAM existente")
    public ResponseEntity<RamDTO> update(@PathVariable Long id, @Valid @RequestBody RamDTO ram) {
        return ResponseEntity.ok(service.actualizar(id, ram));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar una RAM",
            description = "Desactiva una RAM (cambia estado a INACTIVO)")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        service.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una RAM permanentemente",
            description = "Elimina una RAM de la base de datos")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminarPermanente(id);
        return ResponseEntity.noContent().build();
    }
}


package msvc.gpu.Controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msvc.gpu.Assemblers.GpuModelAssembler;
import msvc.gpu.Dto.GpuDTO;
import msvc.gpu.Model.Gpu;
import msvc.gpu.Service.GpuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/gpu")
@Validated
@Tag(name = "GpusV2", description = "Metodos CRUD HATEOAS para la gestión de gpus")
public class GpuControllerV2 {

    private final GpuService service;
    private final GpuModelAssembler gpuModelAssembler;

    public GpuControllerV2(GpuService service,
                           GpuModelAssembler gpuModelAssembler) {
        this.service = service;
        this.gpuModelAssembler = gpuModelAssembler;
    }

    @GetMapping
    @Operation(
            summary = "Listado de GPUs",
            description = "Devuelve todas las tarjetas gráficas registradas en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente"
    )
    public ResponseEntity<CollectionModel<EntityModel<Gpu>>> findAll() {

        List<EntityModel<Gpu>> entityModels = service.findAll()
                .stream()
                .map(gpuModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Gpu>> collectionModel =
                CollectionModel.of(
                        entityModels,
                        linkTo(methodOn(GpuControllerV2.class).findAll()).withSelfRel()
                );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar GPU por ID",
            description = "Devuelve una tarjeta gráfica según el identificador proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "GPU encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Gpu.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "GPU no encontrada"
            )
    })
    public ResponseEntity<EntityModel<Gpu>> findById(

            @Parameter(
                    description = "ID de la GPU",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    ) {

        EntityModel<Gpu> entityModel =
                gpuModelAssembler.toModel(
                        service.findById(id)
                );

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @Operation(
            summary = "Registrar GPU",
            description = "Permite registrar una nueva tarjeta gráfica en el catálogo"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la GPU a registrar",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = GpuDTO.class)
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "GPU creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
    public ResponseEntity<EntityModel<Gpu>> create(
            @Valid @RequestBody GpuDTO d) {

        Gpu gpuCreada = service.create(d);

        EntityModel<Gpu> entityModel =
                gpuModelAssembler.toModel(gpuCreada);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar GPU",
            description = "Actualiza la información de una tarjeta gráfica existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "GPU actualizada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "GPU no encontrada"
            )
    })
    public ResponseEntity<EntityModel<Gpu>> update(

            @Parameter(
                    description = "ID de la GPU a actualizar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody GpuDTO d
    ) {

        Gpu gpuActualizada = service.update(id, d);

        EntityModel<Gpu> entityModel =
                gpuModelAssembler.toModel(gpuActualizada);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desactivar GPU",
            description = "Desactiva una tarjeta gráfica registrada en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "GPU desactivada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "GPU no encontrada"
            )
    })
    public ResponseEntity<Void> delete(

            @Parameter(
                    description = "ID de la GPU a desactivar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    ) {

        service.deactivate(id);

        return ResponseEntity.noContent().build();
    }
}
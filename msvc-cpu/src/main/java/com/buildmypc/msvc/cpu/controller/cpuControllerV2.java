package com.buildmypc.msvc.cpu.controller;


import com.buildmypc.msvc.cpu.Assemblers.cpuModelAssemblers;
import com.buildmypc.msvc.cpu.model.cpu;
import com.buildmypc.msvc.cpu.service.cpuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v2/cpus")
@Validated
@Tag(name = "cpusV2", description = "Métodos CRUD HATEOAS para la gestión de CPUs")
public class cpuControllerV2 {

    @Autowired
    private cpuService CpuService;

    @Autowired
    private cpuModelAssemblers cpuModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de CPUs",
            description = "Devuelve todas las CPUs registradas en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente"
    )
    public ResponseEntity<CollectionModel<EntityModel<cpu>>> findAll() {

        List<EntityModel<cpu>> entityModels = CpuService.getAll()
                .stream()
                .map(cpuModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<cpu>> collectionModel =
                CollectionModel.of(
                        entityModels,
                        linkTo(methodOn(cpuControllerV2.class).findAll()).withSelfRel()
                );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @PostMapping
    @Operation(
            summary = "Registrar CPU",
            description = "Permite registrar una nueva CPU en el catálogo"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la CPU a registrar",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = cpu.class)
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CPU creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
    public ResponseEntity<EntityModel<cpu>> save(
            @Valid @RequestBody cpu cpu) {

        cpu cpuCreada = CpuService.save(cpu);

        EntityModel<cpu> entityModel =
                cpuModelAssembler.toModel(cpuCreada);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar CPU por ID",
            description = "Devuelve una CPU según el identificador proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "CPU encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = cpu.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CPU no encontrada"
            )
    })
    public ResponseEntity<EntityModel<cpu>> findById(

            @Parameter(
                    description = "ID de la CPU",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    ) {

        EntityModel<cpu> entityModel =
                cpuModelAssembler.toModel(
                        CpuService.getById(id)
                );

        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar CPU",
            description = "Actualiza la información de una CPU existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "CPU actualizada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CPU no encontrada"
            )
    })
    public ResponseEntity<EntityModel<cpu>> updateById(

            @Parameter(
                    description = "ID de la CPU a actualizar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody cpu cpu
    ) {

        cpu actualizada =
                CpuService.updateById(id, cpu);

        EntityModel<cpu> entityModel =
                cpuModelAssembler.toModel(actualizada);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar CPU",
            description = "Elimina una CPU registrada en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "CPU eliminada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CPU no encontrada"
            )
    })
    public ResponseEntity<Void> deleteById(

            @Parameter(
                    description = "ID de la CPU a eliminar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    ) {

        CpuService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
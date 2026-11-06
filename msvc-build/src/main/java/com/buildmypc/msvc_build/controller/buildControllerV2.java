package com.buildmypc.msvc_build.controller;

import com.buildmypc.msvc_build.Assemblers.BuildModelAssembler;
import com.buildmypc.msvc_build.dto.buildDto;
import com.buildmypc.msvc_build.model.build;
import com.buildmypc.msvc_build.service.buildService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/builds")
@Validated
@Tag(name = "buildV2", description = "Metodos CRUD HATEOAS para la gestión de builds")

public class buildControllerV2 {

    @Autowired
    private buildService buildService;

    @Autowired
    private BuildModelAssembler buildModelAssembler;


    @GetMapping
    @Operation(
            summary = "Listado de builds",
            description = "Devuelve todas las configuraciones de PC registradas en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente"
    )
    public ResponseEntity<List<buildDto>> findAll() {

        return ResponseEntity.ok(
                buildService.findAll()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar build por ID",
            description = "Devuelve una configuración de PC según el identificador proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Build encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = buildDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Build no encontrada"
            )
    })
    public ResponseEntity<EntityModel<build>> findById(

            @Parameter(
                    description = "ID de la build",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    ) {

        EntityModel<build> entityModel =
                buildModelAssembler.toModel(
                        buildService.getById(id)
                );

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @Operation(
            summary = "Registrar build",
            description = "Permite registrar una nueva configuración de PC"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la build a registrar",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = build.class)
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Build creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
    public ResponseEntity<EntityModel<build>> save(
            @Valid @RequestBody build build) {

        build buildCreada = buildService.save(build);

        EntityModel<build> entityModel =
                buildModelAssembler.toModel(buildCreada);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar build",
            description = "Actualiza la información de una build existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Build actualizada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Build no encontrada"
            )
    })
    public ResponseEntity<EntityModel<build>> updateById(

            @Parameter(
                    description = "ID de la build a actualizar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody build build
    ) {

        build actualizada =
                buildService.updateById(build, id);

        EntityModel<build> entityModel =
                buildModelAssembler.toModel(actualizada);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar build",
            description = "Elimina una configuración de PC registrada en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Build eliminada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Build no encontrada"
            )
    })
    public ResponseEntity<Void> deleteById(

            @Parameter(
                    description = "ID de la build a eliminar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    ) {

        buildService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    @Operation(
            summary = "Cambiar estado de una build",
            description = "Actualiza el estado de una build existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado actualizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Build no encontrada"
            )
    })
    public ResponseEntity<buildDto> cambiarEstado(

            @Parameter(
                    description = "ID de la build",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,

            @Parameter(
                    description = "Nuevo estado de la build",
                    required = true,
                    example = "COMPLETADA"
            )
            @RequestParam String nuevoEstado
    ) {

        return ResponseEntity.ok(
                buildService.cambiarEstado(id, nuevoEstado)
        );
    }
}
package msvc.powersupply.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import msvc.powersupply.Assemblers.psuModelAssembler;
import msvc.powersupply.Model.powerSupply;
import msvc.powersupply.Service.psuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/psus")
public class psuControllerV2 {

    @Autowired
    private psuService psuService;

    @Autowired
    private psuModelAssembler psuModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de fuentes de poder",
            description = "Devuelve todas las fuentes de poder registradas en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente"
    )
    public ResponseEntity<CollectionModel<EntityModel<powerSupply>>> getAllPsu() {

        List<EntityModel<powerSupply>> entityModels = psuService.getAll()
                .stream()
                .map(psuModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<powerSupply>> collectionModel =
                CollectionModel.of(
                        entityModels,
                        linkTo(methodOn(psuControllerV2.class).getAllPsu()).withSelfRel()
                );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar fuente de poder por ID",
            description = "Devuelve una fuente de poder según el ID proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fuente encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = powerSupply.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fuente de poder no encontrada"
            )
    })
    public ResponseEntity<EntityModel<powerSupply>> findById(
            @Parameter(
                    description = "ID de la fuente de poder",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    ) {

        EntityModel<powerSupply> entityModel =
                psuModelAssembler.toModel(
                        psuService.getById(id)
                );

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @Operation(
            summary = "Registrar fuente de poder",
            description = "Permite registrar una nueva fuente de poder en el sistema"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la fuente de poder a registrar",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = powerSupply.class)
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Fuente de poder creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
    public ResponseEntity<EntityModel<powerSupply>> save(
            @Valid @RequestBody powerSupply powerSupply
    ) {

        powerSupply psuCreada = psuService.save(powerSupply);

        EntityModel<powerSupply> entityModel =
                psuModelAssembler.toModel(psuCreada);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar fuente de poder",
            description = "Actualiza la información de una fuente de poder existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fuente actualizada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fuente de poder no encontrada"
            )
    })
    public ResponseEntity<EntityModel<powerSupply>> updateById(

            @Parameter(
                    description = "ID de la fuente de poder a actualizar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody powerSupply powerSupply
    ) {

        powerSupply actualizada =
                psuService.updateById(id, powerSupply);

        EntityModel<powerSupply> entityModel =
                psuModelAssembler.toModel(actualizada);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar fuente de poder",
            description = "Elimina una fuente de poder registrada en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Fuente eliminada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fuente de poder no encontrada"
            )
    })
    public ResponseEntity<Void> deleteById(

            @Parameter(
                    description = "ID de la fuente de poder a eliminar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id
    ) {

        psuService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
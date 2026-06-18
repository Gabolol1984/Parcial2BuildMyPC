package msvc.motherboard.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import msvc.motherboard.Dto.MotherboardDTO;
import msvc.motherboard.Model.Motherboard;
import msvc.motherboard.Service.MotherboardService;
import msvc.motherboard.assemblers.MotherboardModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/v1/motherboards")
@Validated
@Tag(name="motherboards v1", description = "Metodos CRUD para la gestion de las placas mafres (motherboards)")
public class MotherboardController {

    @Autowired
    private MotherboardService service;

    @Autowired
    private MotherboardModelAssembler motherboardModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de todos las motherboards",
            description = "Se devuelve una lista con los motherboards que se encuentran en la tabla motherboards de la DB"

    )
    @Transactional(readOnly = true)
    @ApiResponse(responseCode = "200", description = "Operacion exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Motherboard>>> findAll() {
        List<EntityModel<Motherboard>>entityModels=this.service.getAll()
                .stream()
                .map(motherboardModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<Motherboard>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(MotherboardController.class).findAll()).withSelfRel());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @Transactional
    @PostMapping
    @Operation(summary = "Guardado de motherboard", description = "De esta manera se guarda una motherboard en la BD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Motherboarda a agregar", required = true,
            content = @Content(schema = @Schema(implementation = MotherboardDTO.class))
    )
    public ResponseEntity<Motherboard> save(
            @Valid @RequestBody MotherboardDTO dto) {
        return new ResponseEntity<>(
                service.create(dto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busqueda de una motherboard",
            description = "Se devuelve una motherboard, en caso contrario se devuelve una excepcion"
    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "200",
                    description = "Motherboard encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MotherboardDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo Motherboard",
                                            value = "{\"model\": \"ASUS ROG Strix B550-F\", \"socket\": \"AM4\", \"ramType\": \"DDR4\", \"ramSlots\": 4}"
                                    )
                            }
                    )),
            @ApiResponse(responseCode = "404", description = "Motherboard no se encuentra en la BD")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<EntityModel<Motherboard>> findById(
            @Parameter(description = "Id del motherboard a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        // 1. Buscar la motherboard en el servicio
        Motherboard motherboard = service.getById(id);

        // 2. Pasar la entidad al assembler para que le agregue los links hipermedios (HATEOAS)
        EntityModel<Motherboard> entityModel = this.motherboardModelAssembler.toModel(motherboard);

        // 3. Retornar el EntityModel envuelto en el ResponseEntity
        return ResponseEntity.ok(entityModel);}


}


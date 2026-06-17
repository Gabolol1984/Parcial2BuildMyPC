package msvc.motherboard.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import msvc.motherboard.Dto.MotherboardDTO;
import msvc.motherboard.Model.Motherboard;
import msvc.motherboard.Service.MotherboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/v1/motherboards")
@Validated
@Tag(name="motherboards v1", description = "Metodos CRUD para la gestion de las placas mafres (motherboards)")
public class MotherboardController {

    @Autowired
    private MotherboardService service;

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

    @Operation(
            summary = "Busqueda de una motherboard",
            description = "Se devuelve una motherboard, en caso contrario se devuelve una excepcion"
    )
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<Motherboard> findById(
            @Parameter(description = "Id del motherboard a buscar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @GetMapping
    @Operation(
            summary = "Listado de todos las motherboards",
            description = "Se devuelve una lista con los motherboards que se encuentran en la tabla motherboards de la DB"

    )
    @Transactional(readOnly = true)
    @ApiResponse(responseCode = "200", description = "Operacion exitosa")
    public List<Motherboard> findAll() {
        return service.getAll();
    }
}


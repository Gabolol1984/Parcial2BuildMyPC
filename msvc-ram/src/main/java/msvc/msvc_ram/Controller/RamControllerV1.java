package msvc.msvc_ram.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import msvc.msvc_ram.Dto.RamDTO;
import msvc.msvc_ram.Model.Ram;
import msvc.msvc_ram.Service.RamService;
import msvc.msvc_ram.assemblers.RamModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/ram")
@RequiredArgsConstructor
@Validated
//anotacion tag

@Tag(name = "RAM Controller V1", description = "API para gestión de memorias RAM")
public class RamControllerV1 {

    private final RamService service;

    @GetMapping
    public ResponseEntity<List<RamDTO>> findAll() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RamDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RamDTO> save(@Valid @RequestBody RamDTO ram) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(ram));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RamDTO> update(@PathVariable Long id, @Valid @RequestBody RamDTO ram) {
        return ResponseEntity.ok(service.actualizar(id, ram));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminarPermanente(id);
        return ResponseEntity.noContent().build();
    }
}
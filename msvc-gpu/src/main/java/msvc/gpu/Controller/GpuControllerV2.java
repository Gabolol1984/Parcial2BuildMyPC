package msvc.gpu.Controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msvc.gpu.Dto.GpuDTO;
import msvc.gpu.Model.Gpu;
import msvc.gpu.Service.GpuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/gpu")
@Validated
@Tag(name = "GpusV2", description = "Metodos CRUD HATEOAS para la gestión de gpus")
public class GpuControllerV2 {

    private final GpuService service;

    public GpuControllerV2(GpuService s) {
        this.service = s;
    }

    @GetMapping
    public ResponseEntity<List<Gpu>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gpu> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Gpu> create(@Valid @RequestBody GpuDTO d) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(d));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gpu> update(@PathVariable Long id, @Valid @RequestBody GpuDTO d) {
        return ResponseEntity.ok(service.update(id, d));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
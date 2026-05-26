package msvc.msvc_ram.Controller;

import lombok.RequiredArgsConstructor;
import msvc.msvc_ram.Dto.RamDTO;
import msvc.msvc_ram.Model.Ram;
import msvc.msvc_ram.Service.RamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ram")
@RequiredArgsConstructor
@Validated
public class RamController {


    private final RamService service;

    @GetMapping
    public ResponseEntity<List<Ram>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ram> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Ram> save(@Valid @RequestBody RamDTO ram) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(ram));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ram> update(@PathVariable Long id, @Valid @RequestBody RamDTO ram) {
        return ResponseEntity.ok(service.updateById(id, ram));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

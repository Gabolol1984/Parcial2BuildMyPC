package msvc.motherboard.Controller;

import msvc.motherboard.Dto.MotherboardDTO;
import msvc.motherboard.Model.Motherboard;
import msvc.motherboard.Service.MotherboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/motherboards")
public class MotherboardController {

    @Autowired
    private MotherboardService service;

    @Transactional
    @PostMapping
    public ResponseEntity<Motherboard> save(
            @Valid @RequestBody MotherboardDTO dto) {
        return new ResponseEntity<>(
                service.create(dto),
                HttpStatus.CREATED
        );
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<Motherboard> findById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<Motherboard> findAll() {
        return service.getAll();
    }
}


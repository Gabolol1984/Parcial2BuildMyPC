package com.buildmypc.msvc.cpu.controller;


import com.buildmypc.msvc.cpu.model.cpu;
import com.buildmypc.msvc.cpu.service.cpuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/cpus")
@Validated
@Tag(name = "cpusV2", description = "Metodos CRUD HATEOAS para la gestión de cpus")
public class cpuControllerV2 {

    @Autowired
    private cpuService CpuService;

    @GetMapping
    public ResponseEntity<List<cpu>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CpuService.getAll());
    }

    @PostMapping
    public ResponseEntity<cpu> save(@Valid @RequestBody cpu cpu) {
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CpuService.save(cpu));
    }

    @GetMapping("/{id}")
    public ResponseEntity<cpu> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CpuService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<cpu> updateById(@PathVariable Long id, @Valid @RequestBody cpu cpu) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(CpuService.updateById(id,cpu));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        CpuService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

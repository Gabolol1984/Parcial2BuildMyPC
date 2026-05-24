package com.buildmypc.msvc_build.controller;

import com.buildmypc.msvc_build.dto.buildDto;
import com.buildmypc.msvc_build.model.build;
import com.buildmypc.msvc_build.service.buildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/builds")
@Validated
public class buildController {

    @Autowired
    private buildService buildService;

    @GetMapping
    public ResponseEntity<List<buildDto>> findAll() {
        return ResponseEntity.ok(buildService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<build> findById(@PathVariable Long id) {
        return ResponseEntity.ok(buildService.getById(id));
    }

    @PostMapping
    public ResponseEntity<build> save(@Valid @RequestBody build build) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(buildService.save(build));
    }

    @PutMapping("/{id}")
    public ResponseEntity<build> updateById(@PathVariable Long id, @Valid @RequestBody build build) {
        return ResponseEntity.ok(buildService.updateById(build, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<build> deleteById(@PathVariable Long id) {
        buildService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/estado")
    public ResponseEntity<build> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        return ResponseEntity.(buildService.cambiarEstado(id, nuevoEstado));
    }


}

package com.service.cpu.Controller;

import com.service.cpu.model.cpu;
import com.service.cpu.Service.cpuService;
import com.service.cpu.cpuDTO.cpuDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cpus")
@Validated
public class cpuController {

    @Autowired
    private cpuService CpuService;

    @GetMapping
    public ResponseEntity<List<cpu>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CpuService.getAll());
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

package com.buildmypc.msvc_usuario.controller;

import com.buildmypc.msvc_usuario.model.usuario;
import com.buildmypc.msvc_usuario.service.usuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Validated
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<usuario>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<usuario> findById(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.getById(id));
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<usuario> findByEmail(@PathVariable String email){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.getByEmail(email));
    }

    @PostMapping
    public ResponseEntity<usuario> save(@Valid @RequestBody usuario usuario){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<usuario> updateById(@PathVariable Long id, @Valid @RequestBody usuario usuario){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.updateById(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        usuarioService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }
}

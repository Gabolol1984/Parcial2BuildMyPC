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
@RequestMapping("/api/v2/ram")
@RequiredArgsConstructor
@Validated
//anotacion tag
@Tag()
public class RamController {


    @Autowired
    private  RamService service;


    @Autowired


    @GetMapping
    //altero findall
    public ResponseEntity<CollectionModel<EntityModel<Ram>>> findAll() {
        List<EntityModel<Ram>> entityModels = this.service.findAll()
                .stream()
                .map(RamModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<Ram>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo()
        );

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
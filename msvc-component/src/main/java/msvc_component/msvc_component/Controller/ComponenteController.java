package msvc_component.msvc_component.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import msvc_component.msvc_component.Model.Componente;
import msvc_component.msvc_component.Service.ComponenteService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/componentes")
public class ComponenteController {
    @Autowired
    private ComponenteService componenteService;

    @Transactional(readOnly = true)
    @GetMapping
    public List<Componente> findAll() {

        return this.componenteService.findAll();
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<Componente> findById(@PathVariable Long id) {

        return ResponseEntity.ok(this.componenteService.findById(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Componente> save(@Valid @RequestBody Componente componente) {

        return new ResponseEntity<>(this.componenteService.save(componente), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        this.componenteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Componente> updateById(@PathVariable Long id, @RequestBody Componente componente) {
        return ResponseEntity.ok(this.componenteService.updateById(id, componente));
    }
}

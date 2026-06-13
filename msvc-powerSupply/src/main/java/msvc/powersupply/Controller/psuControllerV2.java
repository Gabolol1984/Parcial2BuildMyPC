package msvc.powersupply.Controller;

import jakarta.validation.Valid;
import msvc.powersupply.Model.powerSupply;
import msvc.powersupply.Service.psuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/psus")
public class psuControllerV2 {

    @Autowired
    private psuService psuService;

    @GetMapping
    public ResponseEntity<List<powerSupply>> getAllPsu(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(psuService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<powerSupply> findById(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(psuService.getById(id));
    }

    @PostMapping
    public ResponseEntity<powerSupply> save(@Valid @RequestBody powerSupply powerSupply){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(psuService.save(powerSupply));
    }

    @PutMapping("/{id}")
    public ResponseEntity<powerSupply> updateById(@PathVariable Long id, @Valid @RequestBody powerSupply powerSupply){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(psuService.updateById(id, powerSupply));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        psuService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
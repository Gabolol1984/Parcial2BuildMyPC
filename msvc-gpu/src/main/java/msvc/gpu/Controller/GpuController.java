package msvc.gpu.Controller;

import msvc.gpu.Dto.GpuDTO;
import msvc.gpu.Model.Gpu;
import msvc.gpu.Service.GpuService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/gpu")
public class GpuController {

    private final GpuService service;

    public GpuController(GpuService s){
        this.service = s;
    }

    @GetMapping
    public List<Gpu> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Gpu findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    public Gpu create(@Valid @RequestBody GpuDTO d){
        return service.create(d);
    }

    @PutMapping("/{id}")
    public Gpu update(@PathVariable Long id, @RequestBody GpuDTO d){
        return service.update(id, d);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deactivate(id);
    }
}

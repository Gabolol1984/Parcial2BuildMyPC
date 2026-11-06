package msvc.gpu.Service;

import msvc.gpu.Dto.GpuDTO;
import msvc.gpu.Exception.GpuException;
import msvc.gpu.Model.Gpu;
import msvc.gpu.Repository.GpuRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GpuServiceImpl implements GpuService {

    private final GpuRepository repo;

    public GpuServiceImpl(GpuRepository r){
        this.repo = r;
    }

    @Override
    public Gpu create(GpuDTO d){
        Gpu g = new Gpu();
        g.setMarca(d.getMarca());
        g.setModelo(d.getModelo());
        g.setPrecioBase(d.getPrecioBase());
        g.setTipoMemoria(d.getTipoMemoria());
        g.setMemoriaGb(d.getMemoriaGb());
        g.setConsumoW(d.getConsumoW());
        g.setDescripcion(d.getDescripcion());
        return repo.save(g);
    }

    @Override
    public List<Gpu> findAll(){
        return repo.findAll();
    }

    @Override
    public Gpu findById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new GpuException("GPU no encontrada"));
    }

    @Override
    public Gpu update(Long id, GpuDTO d){
        Gpu g = findById(id);
        g.setMarca(d.getMarca());
        g.setModelo(d.getModelo());
        g.setPrecioBase(d.getPrecioBase());
        g.setTipoMemoria(d.getTipoMemoria());
        g.setMemoriaGb(d.getMemoriaGb());
        g.setConsumoW(d.getConsumoW());
        g.setDescripcion(d.getDescripcion());
        return repo.save(g);
    }

    @Override
    public void deactivate(Long id){
        repo.deleteById(id);
    }
}
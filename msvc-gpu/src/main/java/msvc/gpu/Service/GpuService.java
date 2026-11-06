package msvc.gpu.Service;

import msvc.gpu.Dto.GpuDTO;
import msvc.gpu.Model.Gpu;

import java.util.List;

public interface GpuService {

    Gpu create(GpuDTO d);

    List<Gpu> findAll();

    Gpu findById(Long id);

    Gpu update(Long id, GpuDTO d);

    void deactivate(Long id);
}
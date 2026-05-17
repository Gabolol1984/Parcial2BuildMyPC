package msvc_gpu.msvc_gpu.Service;

import msvc_gpu.msvc_gpu.Dto.GpuDTO;
import msvc_gpu.msvc_gpu.Model.Gpu;

import java.util.List;

public interface GpuService {

    Gpu create(GpuDTO d);

    List<Gpu> findAll();

    Gpu findById(Long id);

    Gpu update(Long id, GpuDTO d);

    void deactivate(Long id);
}
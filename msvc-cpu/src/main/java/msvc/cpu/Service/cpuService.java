package msvc.cpu.Service;

import msvc.cpu.model.cpu;

import java.util.List;

public interface cpuService {
    List<cpu> getAll();
    cpu getById(Long id);
    cpu updateById(Long id,cpu cpu);
    void delete(Long id);
}

package msvc.msvc_ram.Service;

import msvc.msvc_ram.Dto.RamDTO;
import msvc.msvc_ram.Model.Ram;

import java.util.List;

public interface RamService {
    List<Ram> findAll();
    Ram findById(Long id);
    Ram save(Ram ram);
    Ram updateById(Long id, Ram ram);
    void deleteById(Long id);


}

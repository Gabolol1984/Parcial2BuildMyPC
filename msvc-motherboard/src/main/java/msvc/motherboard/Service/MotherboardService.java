package msvc.motherboard.Service;

import msvc.motherboard.Dto.MotherboardDTO;
import msvc.motherboard.Model.Motherboard;

import java.util.List;

public interface MotherboardService {
    Motherboard create(MotherboardDTO dto);
    List<Motherboard> getAll();
    Motherboard getById(Long id);
    Motherboard update(Long id, MotherboardDTO dto);
    void delete(Long id);
}
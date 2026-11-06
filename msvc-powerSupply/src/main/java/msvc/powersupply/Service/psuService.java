package msvc.powersupply.Service;


import msvc.powersupply.Model.powerSupply;

import java.util.List;

public interface psuService {
    List<powerSupply> getAll();
    powerSupply getById(Long id);
    powerSupply save(powerSupply powerSupply);
    powerSupply updateById(Long id,powerSupply cpu);
    void deleteById(Long id);
}

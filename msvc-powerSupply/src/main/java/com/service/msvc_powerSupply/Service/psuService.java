package com.service.msvc_powerSupply.Service;


import com.service.msvc_powerSupply.Model.powerSupply;

import java.util.List;

public interface psuService {
    List<powerSupply> getAll();
    powerSupply getById(Long id);
    powerSupply updateById(Long id,powerSupply cpu);
    void deleteById(Long id);
}

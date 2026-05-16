package com.service.cpu.Service;

import com.service.cpu.model.cpu;
import com.service.cpu.cpuDTO.cpuDTO;

import java.util.List;

public interface cpuService {
    List<cpu> getAll();
    cpu getById(Long id);
    cpu updateById(Long id,cpu cpu);
    void delete(Long id);
}

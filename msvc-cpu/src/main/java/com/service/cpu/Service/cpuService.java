package com.service.cpu.Service;

import com.service.cpu.model.cpu;
import com.service.cpu.cpuDTO.cpuDTO;

import java.util.List;

public interface cpuService {
    cpu create(cpuDTO dto);
    List<cpu> getAll();
    cpu getById(Long id);
    cpu update(cpuDTO dto);
    void delete(Long id);
}

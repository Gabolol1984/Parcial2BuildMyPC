package com.buildmypc.msvc.cpu.service;

import com.buildmypc.msvc.cpu.model.cpu;

import java.util.List;

public interface cpuService {
    List<cpu> getAll();
    cpu getById(Long id);
    cpu updateById(Long id,cpu cpu);
    void delete(Long id);
}

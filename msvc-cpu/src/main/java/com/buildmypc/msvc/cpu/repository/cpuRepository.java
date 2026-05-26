package com.buildmypc.msvc.cpu.repository;

import com.buildmypc.msvc.cpu.model.cpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface cpuRepository extends JpaRepository<cpu, Long> {
    List<cpu> findBySocket(String socket);
    Optional<cpu> findById(Long id);
    Optional<cpu> findBycpuName(String cpuName);
    cpu save(cpu cpu);
    List<cpu> findByGeneracion(String generacion);
    List<cpu> id(Long id);
}

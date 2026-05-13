package com.service.cpu.Repository;

import com.service.cpu.model.cpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface cpuRepository extends JpaRepository<cpu, Long> {
    List<cpu> findBySocket(String socket);
    List<cpu> findByGeneracion(String generacion);

}

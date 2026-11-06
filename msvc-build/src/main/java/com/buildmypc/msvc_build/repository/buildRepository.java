package com.buildmypc.msvc_build.repository;


import com.buildmypc.msvc_build.model.build;
import com.buildmypc.msvc_build.model.build.EstadoBuild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface buildRepository extends JpaRepository<build,Long>{
    List<build> findByUsuarioId(Long usuarioId);
    List<build> findByEstado(EstadoBuild estado);
    List<build> findByUsuarioIdAndEstado(Long usuarioId, EstadoBuild estado);
    // Usado por compatibility-service y quotation-service para consultar builds por cpuId
    List<build> findByCpuId(Long cpuId);
    boolean existsByUsuarioIdAndEstado(Long usuarioId, EstadoBuild estado);

}

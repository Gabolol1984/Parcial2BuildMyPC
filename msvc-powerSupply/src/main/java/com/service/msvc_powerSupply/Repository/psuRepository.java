package com.service.msvc_powerSupply.Repository;

import com.service.msvc_powerSupply.Model.powerSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface psuRepository extends JpaRepository<powerSupply,Long> {

    Optional<powerSupply> findById(Long id);
    Optional<powerSupply> findByCertificacion(String certificacion);
}

package com.buildmypc.msvc_quotation.repository;

import com.buildmypc.msvc_quotation.model.DetalleCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Long> {
    List<DetalleCotizacion> findByCotizacionId(Long cotizacionId);
    void deleteByCotizacionId(Long cotizacionId);
}

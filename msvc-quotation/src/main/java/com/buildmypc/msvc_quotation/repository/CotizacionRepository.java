package com.buildmypc.msvc_quotation.repository;

import com.buildmypc.msvc_quotation.model.Cotizacion;
import com.buildmypc.msvc_quotation.model.Cotizacion.EstadoCotizacion;
import com.buildmypc.msvc_quotation.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    List<Cotizacion> findByUsuarioId(Long id);

    List<Cotizacion> findByEstado(EstadoCotizacion estado);

    Optional<Cotizacion> findByBuildId(Long buildId);

    // Para vencer cotizaciones pendientes cuya fecha ya pasó
    List<Cotizacion> findByEstadoAndFechaVencimientoBefore(
            EstadoCotizacion estado, LocalDateTime ahora);
}

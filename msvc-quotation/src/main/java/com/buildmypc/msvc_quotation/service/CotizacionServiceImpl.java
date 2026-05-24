package com.buildmypc.msvc_quotation.service;

import com.buildmypc.msvc_quotation.dto.CotizacionRequestDTO;
import com.buildmypc.msvc_quotation.dto.CotizacionResponseDTO;
import com.buildmypc.msvc_quotation.dto.DetalleCotizacionDTO;
import com.buildmypc.msvc_quotation.exception.ResourceNotFoundException;
import com.buildmypc.msvc_quotation.exception.ServiceException;
import com.buildmypc.msvc_quotation.feign.BuildClient;
import com.buildmypc.msvc_quotation.feign.ComponenteClient;
import com.buildmypc.msvc_quotation.feign.dto.BuildDTO;
import com.buildmypc.msvc_quotation.feign.dto.ComponenteDTO;
import com.buildmypc.msvc_quotation.model.Cotizacion;
import com.buildmypc.msvc_quotation.model.Cotizacion.EstadoCotizacion;
import com.buildmypc.msvc_quotation.model.DetalleCotizacion;
import com.buildmypc.msvc_quotation.repository.CotizacionRepository;
import com.buildmypc.msvc_quotation.repository.DetalleCotizacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CotizacionServiceImpl implements CotizacionService {
    private static final Logger log = LoggerFactory.getLogger(CotizacionServiceImpl.class);

    private final CotizacionRepository cotizacionRepository;
    private final DetalleCotizacionRepository detalleRepository;
    private final BuildClient buildClient;
    private final ComponenteClient componenteClient;

    // ─────────────────────────────────────────────────
    // CREAR cotización — flujo principal del negocio
    // ─────────────────────────────────────────────────
    @Override
    @Transactional
    public CotizacionResponseDTO crear(CotizacionRequestDTO dto) {
        log.info("Creando cotización para buildId={}, usuarioId={}",
                dto.getBuildId(), dto.getUsuarioId());

        // Regla: consulta la build en build-service
        BuildDTO build = buildClient.getBuildById(dto.getBuildId());

        // Regla: no se puede cotizar una build incompatible o incompleta
        if (Boolean.FALSE.equals(build.getCompleta())) {
            throw new ServiceException(
                    "No se puede cotizar una build incompleta. Faltan componentes.");
        }
        if ("INCOMPATIBLE".equalsIgnoreCase(build.getEstado())) {
            throw new ServiceException(
                    "No se puede cotizar una build marcada como INCOMPATIBLE.");
        }
        if (!"VALIDADA".equalsIgnoreCase(build.getEstado())) {
            throw new ServiceException(
                    "La build debe estar en estado VALIDADA para cotizarse. "
                            + "Estado actual: " + build.getEstado());
        }

        // Regla: no puede haber dos cotizaciones activas para la misma build
        cotizacionRepository.findByBuildId(dto.getBuildId()).ifPresent(c -> {
            if (c.getEstado() == EstadoCotizacion.PENDIENTE) {
                throw new ServiceException(
                        "Ya existe una cotización PENDIENTE para la build id: "
                                + dto.getBuildId());
            }
        });

        // Consulta precios de cada componente en component-service
        List<DetalleCotizacion> detalles = new ArrayList<>();
        detalles.addAll(agregarDetalle(build.getCpuId(),         "CPU"));
        detalles.addAll(agregarDetalle(build.getGpuId(),         "GPU"));
        detalles.addAll(agregarDetalle(build.getMotherboardId(), "Motherboard"));
        detalles.addAll(agregarDetalle(build.getRamId(),         "RAM"));
        detalles.addAll(agregarDetalle(build.getFuenteId(),      "Fuente de poder"));

        // Calcula subtotal sumando precios de todos los componentes
        double subtotal = detalles.stream()
                .mapToDouble(DetalleCotizacion::getPrecio)
                .sum();

        double descuento = dto.getDescuento() != null ? dto.getDescuento() : 0.0;

        // Regla: el descuento no puede superar el subtotal
        if (descuento > subtotal) {
            throw new ServiceException(
                    "El descuento ($" + descuento + ") no puede superar el subtotal ($"
                            + subtotal + ").");
        }

        double total = subtotal - descuento;

        // Persiste la cotización
        Cotizacion cotizacion = Cotizacion.builder()
                .buildId(dto.getBuildId())
                .usuarioId(dto.getUsuarioId())
                .subtotal(subtotal)
                .descuento(descuento)
                .total(total)
                .build();

        Cotizacion guardada = cotizacionRepository.save(cotizacion);
        log.info("Cotización id={} creada, total=${}", guardada.getId(), total);

        // Persiste cada detalle con el id de la cotización recién creada
        detalles.forEach(d -> d.setCotizacionId(guardada.getId()));
        detalleRepository.saveAll(detalles);

        // Notifica a build-service que la build pasó a COTIZADA
        buildClient.cambiarEstado(dto.getBuildId(), "COTIZADA");
        log.info("Build id={} actualizada a estado COTIZADA", dto.getBuildId());

        return toDTO(guardada, detalles);
    }

    // ─────────────────────────────────────────────────
    // CRUD base
    // ─────────────────────────────────────────────────
    @Override
    public List<CotizacionResponseDTO> listarTodas() {
        log.info("Listando todas las cotizaciones");
        return cotizacionRepository.findAll().stream()
                .map(c -> toDTO(c, detalleRepository.findByCotizacionId(c.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<CotizacionResponseDTO> listarPorUsuario(Long usuarioId) {
        log.info("Listando cotizaciones del usuarioId={}", usuarioId);
        return cotizacionRepository.findByUsuarioId(usuarioId).stream()
                .map(c -> toDTO(c, detalleRepository.findByCotizacionId(c.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<CotizacionResponseDTO> listarPorEstado(String estado) {
        log.info("Listando cotizaciones por estado={}", estado);
        EstadoCotizacion estadoEnum = parsearEstado(estado);
        return cotizacionRepository.findByEstado(estadoEnum).stream()
                .map(c -> toDTO(c, detalleRepository.findByCotizacionId(c.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public CotizacionResponseDTO buscarPorId(Long id) {
        log.info("Buscando cotización id={}", id);
        Cotizacion c = obtenerOLanzar(id);
        return toDTO(c, detalleRepository.findByCotizacionId(id));
    }

    @Override
    public CotizacionResponseDTO buscarPorBuild(Long buildId) {
        log.info("Buscando cotización por buildId={}", buildId);
        Cotizacion c = cotizacionRepository.findByBuildId(buildId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe cotización para la build id: " + buildId));
        return toDTO(c, detalleRepository.findByCotizacionId(c.getId()));
    }

    // ─────────────────────────────────────────────────
    // APROBAR cotización
    // ─────────────────────────────────────────────────
    @Override
    @Transactional
    public CotizacionResponseDTO aprobar(Long id) {
        log.info("Aprobando cotización id={}", id);
        Cotizacion cotizacion = obtenerOLanzar(id);

        if (cotizacion.getEstado() != EstadoCotizacion.PENDIENTE) {
            throw new ServiceException(
                    "Solo se pueden aprobar cotizaciones PENDIENTES. Estado actual: "
                            + cotizacion.getEstado());
        }

        cotizacion.setEstado(EstadoCotizacion.APROBADA);
        Cotizacion actualizada = cotizacionRepository.save(cotizacion);

        // Notifica a build-service que la build fue APROBADA
        buildClient.cambiarEstado(cotizacion.getBuildId(), "APROBADA");
        log.info("Cotización id={} aprobada. Build id={} actualizada a APROBADA",
                id, cotizacion.getBuildId());

        return toDTO(actualizada, detalleRepository.findByCotizacionId(id));
    }

    // ─────────────────────────────────────────────────
    // RECHAZAR cotización
    // ─────────────────────────────────────────────────
    @Override
    @Transactional
    public CotizacionResponseDTO rechazar(Long id) {
        log.info("Rechazando cotización id={}", id);
        Cotizacion cotizacion = obtenerOLanzar(id);

        if (cotizacion.getEstado() != EstadoCotizacion.PENDIENTE) {
            throw new ServiceException(
                    "Solo se pueden rechazar cotizaciones PENDIENTES. Estado actual: "
                            + cotizacion.getEstado());
        }

        cotizacion.setEstado(EstadoCotizacion.RECHAZADA);
        Cotizacion actualizada = cotizacionRepository.save(cotizacion);
        log.info("Cotización id={} rechazada", id);

        return toDTO(actualizada, detalleRepository.findByCotizacionId(id));
    }

    // ─────────────────────────────────────────────────
    // TAREA PROGRAMADA: vence cotizaciones expiradas
    // Se ejecuta todos los días a las 00:00
    // ─────────────────────────────────────────────────
    @Override
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void vencerCotizacionesExpiradas() {
        log.info("Ejecutando tarea: vencer cotizaciones expiradas");

        List<Cotizacion> vencidas = cotizacionRepository
                .findByEstadoAndFechaVencimientoBefore(
                        EstadoCotizacion.PENDIENTE, LocalDateTime.now());

        vencidas.forEach(c -> {
            c.setEstado(EstadoCotizacion.VENCIDA);
            log.info("Cotización id={} marcada como VENCIDA (venció el {})",
                    c.getId(), c.getFechaVencimiento());
        });

        cotizacionRepository.saveAll(vencidas);
        log.info("Total cotizaciones vencidas procesadas: {}", vencidas.size());
    }

    // ─────────────────────────────────────────────────
    // Helpers privados
    // ─────────────────────────────────────────────────

    /**
     * Consulta component-service y construye un DetalleCotizacion.
     * Retorna lista para poder usar addAll() fácilmente.
     */
    private List<DetalleCotizacion> agregarDetalle(Long componenteId, String tipoFallback) {
        if (componenteId == null) return List.of();

        ComponenteDTO comp = componenteClient.getComponenteById(componenteId);

        String nombre = comp.getMarca() + " " + comp.getModelo()
                + " (" + comp.getTipo() + ")";

        return List.of(DetalleCotizacion.builder()
                .componenteId(comp.getId())
                .nombre(nombre)
                .precio(comp.getPrecioBase())
                .build());
    }

    private Cotizacion obtenerOLanzar(Long id) {
        return cotizacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cotización no encontrada id={}", id);
                    return new ResourceNotFoundException(
                            "Cotización no encontrada con id: " + id);
                });
    }

    private EstadoCotizacion parsearEstado(String estado) {
        try {
            return EstadoCotizacion.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Estado inválido: " + estado
                    + ". Valores válidos: PENDIENTE, APROBADA, RECHAZADA, VENCIDA");
        }
    }

    // ─────────────────────────────────────────────────
    // Mapper
    // ─────────────────────────────────────────────────
    private CotizacionResponseDTO toDTO(Cotizacion c, List<DetalleCotizacion> detalles) {
        List<DetalleCotizacionDTO> detallesDTO = detalles.stream()
                .map(d -> DetalleCotizacionDTO.builder()
                        .id(d.getId())
                        .componenteId(d.getComponenteId())
                        .nombre(d.getNombre())
                        .precio(d.getPrecio())
                        .build())
                .collect(Collectors.toList());

        return CotizacionResponseDTO.builder()
                .id(c.getId())
                .buildId(c.getBuildId())
                .usuarioId(c.getUsuarioId())
                .subtotal(c.getSubtotal())
                .descuento(c.getDescuento())
                .total(c.getTotal())
                .estado(c.getEstado())
                .fechaEmision(c.getFechaEmision())
                .fechaVencimiento(c.getFechaVencimiento())
                .detalles(detallesDTO)
                .build();
    }
}

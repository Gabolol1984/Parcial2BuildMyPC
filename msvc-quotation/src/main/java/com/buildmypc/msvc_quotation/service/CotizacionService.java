package com.buildmypc.msvc_quotation.service;

import com.buildmypc.msvc_quotation.dto.CotizacionRequestDTO;
import com.buildmypc.msvc_quotation.dto.CotizacionResponseDTO;

import java.util.List;

public interface CotizacionService {
    CotizacionResponseDTO crear(CotizacionRequestDTO dto);

    List<CotizacionResponseDTO> listarTodas();

    List<CotizacionResponseDTO> listarPorUsuario(Long usuarioId);

    List<CotizacionResponseDTO> listarPorEstado(String estado);

    CotizacionResponseDTO buscarPorId(Long id);

    CotizacionResponseDTO buscarPorBuild(Long buildId);

    CotizacionResponseDTO aprobar(Long id);

    CotizacionResponseDTO rechazar(Long id);

    // Tarea programada: anula cotizaciones PENDIENTES vencidas
    void vencerCotizacionesExpiradas();
}

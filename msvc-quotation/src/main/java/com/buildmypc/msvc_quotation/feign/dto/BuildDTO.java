package com.buildmypc.msvc_quotation.feign.dto;

import lombok.Data;

@Data
public class BuildDTO {
    private Long id;
    private Long usuarioId;
    private Long cpuId;
    private Long gpuId;
    private Long motherboardId;
    private Long ramId;
    private Long fuenteId;
    private String estado;       // debe ser VALIDADA para cotizar
    private Boolean completa;
}

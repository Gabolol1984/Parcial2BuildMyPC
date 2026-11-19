package com.buildmypc.msvc_quotation.feign.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public Boolean getCompleta() {
        if (this.completa != null) {
            return this.completa;
        }
        return cpuId != null && gpuId != null && motherboardId != null && ramId != null && fuenteId != null;
    }
}

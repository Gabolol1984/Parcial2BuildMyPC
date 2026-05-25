package com.buildmypc.msvc_build.dto;

import lombok.Data;

@Data
public class powersupplyDTO {

    private Long componenteId;
    private Integer potenciaWatts;
    private String certificacion;
    private Boolean modular;
    private Integer conectoresPcie;
    private String estado;
}
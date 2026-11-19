package com.buildmypc.msvc_build.dto;

import lombok.Data;

@Data
public class ramDTO {

    private Long id;
    private Long componenteId;
    private String tipoDdr;
    private Integer capacidadGb;
    private Integer frecuenciaMhz;
    private String latenciaCl;
    private Integer modulos;
    private Double voltaje;
}

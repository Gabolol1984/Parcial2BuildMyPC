package com.buildmypc.msvc_build.dto;

import lombok.Data;

@Data
public class ramDTO {

    private Long componenteId;
    private String tipoDdr;
    private Integer capacidadGb;
    private Integer frecuenciaMhz;
    private Integer latenciaCl;
    private Integer modulos;
    private Double voltaje;
}
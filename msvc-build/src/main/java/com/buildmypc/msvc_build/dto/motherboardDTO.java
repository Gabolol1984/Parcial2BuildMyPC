package com.buildmypc.msvc_build.dto;

import lombok.Data;

@Data
public class motherboardDTO {

    private Long componenteId;
    private String socket;
    private String chipset;
    private String tipoRamSoportada;
    private Integer slotsRam;
    private Integer maxRamGb;
    private String formato;
    private String estado;
}
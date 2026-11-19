package com.buildmypc.msvc_build.dto;

import lombok.Data;

@Data
public class motherboardDTO {

    private Long id;
    private Long componenteId;
    private String model;
    private String socket;
    private String chipset;
    private String ramType;
    private String tipoRamSoportada;
    private Integer ramSlots;
    private Integer slotsRam;
    private Integer maxRamGb;
    private String formato;
    private String estado;
}

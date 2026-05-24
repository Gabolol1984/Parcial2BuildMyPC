package com.buildmypc.msvc_quotation.feign.dto;

import lombok.Data;

@Data
public class ComponenteDTO {
    private Long id;
    private String tipo;
    private String marca;
    private String modelo;
    private Double precioBase;
    private String estado;
}

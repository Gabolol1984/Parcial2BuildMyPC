package com.buildmypc.msvc_quotation.feign.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponenteDTO {
    @JsonAlias("componenteId")
    private Long id;
    private String tipo;
    private String marca;
    private String modelo;
    private Double precioBase;
    private String estado;
}

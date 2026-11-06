package com.buildmypc.msvc_build.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class cpuDTO {
    private Long id;
    private String socket;
    private Integer nucleos;
    private String generacion;
    private Boolean soportaDdr4;
    private Boolean soportaDdr5;
}

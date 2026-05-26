package com.buildmypc.msvc.cpu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "El socket es obligatorio")
    private String socket;

    @NotNull(message = "La cantidad de nucleos es obligatoria")
    private Integer nucleos;

    private String generacion;

    private Boolean soportaDdr4;

    private Boolean soportaDdr5;
}

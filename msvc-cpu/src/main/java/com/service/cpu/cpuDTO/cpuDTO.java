package com.service.cpu.cpuDTO;

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
    @NotBlank(message = "El socket es obligatorio")
    private String socket;

    @NotBlank(message = "La cantidad de nucleos es obligatoria")
    private Integer nucleos;

    private String generacion;

    private Boolean soportaDdr4;

    private Boolean soportaDdr5;
}

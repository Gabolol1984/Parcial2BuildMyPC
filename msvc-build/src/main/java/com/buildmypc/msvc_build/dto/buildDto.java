package com.buildmypc.msvc_build.dto;

import com.buildmypc.msvc_build.model.build.EstadoBuild;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class buildDto {

    @NotNull(message = "El usuarioId es obligatorio")
    private Long Id;

    private Long cpuId;
    private Long gpuId;
    private Long motherboardId;
    private Long ramId;
    private Long fuenteId;
    private Long usuarioId;
    private listDTO list;
    private EstadoBuild estado;


}

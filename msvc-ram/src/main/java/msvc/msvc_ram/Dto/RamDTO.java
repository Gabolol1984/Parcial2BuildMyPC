package msvc.msvc_ram.Dto;

import lombok.Data;

@Data
public class RamDTO {

    private Long componenteId;
    private String tipoDdr;
    private Integer capacidadGb;
    private Integer frecuenciaMhz;
    private String latenciaCl;
    private Integer modulos;
    private Double voltaje;
}
package msvc.gpu.Dto;

import lombok.Data;

@Data
public class GpuDTO {
    private String marca;
    private String modelo;
    private Double precioBase;
    private String tipoMemoria;
    private Integer memoriaGb;
    private Integer consumoW;
    private String descripcion;
}
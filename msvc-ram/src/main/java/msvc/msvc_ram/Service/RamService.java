package msvc.msvc_ram.Service;

import msvc.msvc_ram.Dto.RamDTO;

import java.util.List;

public interface RamService {
    RamDTO crear(RamDTO dto);

    List<RamDTO> listarTodos();

    List<RamDTO> listarPorTipo(String tipo);

    List<RamDTO> listarPorEstado(String estado);

    List<RamDTO> listarPorMarca(String marca);

    List<RamDTO> listarPorTipoDdr(String tipoDdr);

    List<RamDTO> listarPorCapacidadMinima(Integer capacidadMinima);

    RamDTO buscarPorId(Long id);

    RamDTO buscarPorModelo(String modelo);

    RamDTO actualizar(Long id, RamDTO dto);

    void desactivar(Long id);

    void eliminarPermanente(Long id);
}


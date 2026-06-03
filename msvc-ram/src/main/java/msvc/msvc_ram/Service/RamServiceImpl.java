package msvc.msvc_ram.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import msvc.msvc_ram.Dto.RamDTO;
import msvc.msvc_ram.Exception.RamException;
import msvc.msvc_ram.Model.Ram;
import msvc.msvc_ram.Repository.RamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class RamServiceImpl implements RamService {

    @Autowired
    private RamRepository ramRepository;

    @Override
    public RamDTO crear(RamDTO dto) {
        // Validar que no exista un modelo duplicado
        if (ramRepository.findByModelo(dto.getModelo()).isPresent()) {
            throw new RuntimeException("Ya existe una RAM con el modelo: " + dto.getModelo());
        }

        Ram ram = convertToEntity(dto);
        ram.setEstado("DISPONIBLE");
        ram.setActivo(true);

        Ram savedRam = ramRepository.save(ram);
        return convertToDTO(savedRam);
    }

    @Override
    public List<RamDTO> listarTodos() {
        return ramRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RamDTO> listarPorTipo(String tipo) {
        return ramRepository.findByTipo(tipo)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RamDTO> listarPorEstado(String estado) {
        return ramRepository.findByEstado(estado)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RamDTO> listarPorMarca(String marca) {
        return ramRepository.findByMarca(marca)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RamDTO> listarPorTipoDdr(String tipoDdr) {
        return ramRepository.findByTipoDdr(tipoDdr)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RamDTO> listarPorCapacidadMinima(Integer capacidadMinima) {
        return ramRepository.findByCapacidadGbGreaterThanEqual(capacidadMinima)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RamDTO buscarPorId(Long id) {
        Ram ram = ramRepository.findById(id)
                .orElseThrow(() -> new RamException("RAM no encontrada con ID: " + id));
        return convertToDTO(ram);
    }

    @Override
    public RamDTO buscarPorModelo(String modelo) {
        Ram ram = ramRepository.findByModelo(modelo)
                .orElseThrow(() -> new RamException("RAM no encontrada con modelo: " + modelo));
        return convertToDTO(ram);
    }

    @Override
    public RamDTO actualizar(Long id, RamDTO dto) {
        Ram ram = ramRepository.findById(id)
                .orElseThrow(() -> new RamException("RAM no encontrada con ID: " + id));

        // Actualizar solo los campos permitidos
        ram.setTipo(dto.getTipo());
        ram.setMarca(dto.getMarca());
        ram.setModelo(dto.getModelo());
        ram.setPrecioBase(dto.getPrecioBase());
        ram.setDescripcion(dto.getDescripcion());
        ram.setFechaLanzamiento(dto.getFechaLanzamiento());
        ram.setTipoDdr(dto.getTipoDdr());
        ram.setCapacidadGb(dto.getCapacidadGb());
        ram.setFrecuenciaMhz(dto.getFrecuenciaMhz());
        ram.setLatenciaCl(dto.getLatenciaCl());
        ram.setModulos(dto.getModulos());
        ram.setVoltaje(dto.getVoltaje());
        // No actualizar estado, activo desde el DTO por seguridad

        Ram updatedRam = ramRepository.save(ram);
        return convertToDTO(updatedRam);
    }

    @Override
    public void desactivar(Long id) {
        Ram ram = ramRepository.findById(id)
                .orElseThrow(() -> new RamException("RAM no encontrada con ID: " + id));
        ram.setActivo(false);
        ram.setEstado("INACTIVO");
        ramRepository.save(ram);
    }

    @Override
    public void eliminarPermanente(Long id) {
        if (!ramRepository.existsById(id)) {
            throw new RamException("RAM no encontrada con ID: " + id);
        }
        ramRepository.deleteById(id);
    }

    // Métodos de conversión
    private Ram convertToEntity(RamDTO dto) {
        Ram ram = new Ram();
        ram.setTipo(dto.getTipo());
        ram.setMarca(dto.getMarca());
        ram.setModelo(dto.getModelo());
        ram.setPrecioBase(dto.getPrecioBase());
        ram.setDescripcion(dto.getDescripcion());
        ram.setFechaLanzamiento(dto.getFechaLanzamiento());
        ram.setTipoDdr(dto.getTipoDdr());
        ram.setCapacidadGb(dto.getCapacidadGb());
        ram.setFrecuenciaMhz(dto.getFrecuenciaMhz());
        ram.setLatenciaCl(dto.getLatenciaCl());
        ram.setModulos(dto.getModulos());
        ram.setVoltaje(dto.getVoltaje());
        return ram;
    }

    private RamDTO convertToDTO(Ram ram) {
        RamDTO dto = new RamDTO();
        dto.setId(ram.getComponenteId());
        dto.setTipo(ram.getTipo());
        dto.setMarca(ram.getMarca());
        dto.setModelo(ram.getModelo());
        dto.setPrecioBase(ram.getPrecioBase());
        dto.setEstado(ram.getEstado());
        dto.setDescripcion(ram.getDescripcion());
        dto.setFechaLanzamiento(ram.getFechaLanzamiento());
        dto.setTipoDdr(ram.getTipoDdr());
        dto.setCapacidadGb(ram.getCapacidadGb());
        dto.setFrecuenciaMhz(ram.getFrecuenciaMhz());
        dto.setLatenciaCl(ram.getLatenciaCl());
        dto.setModulos(ram.getModulos());
        dto.setVoltaje(ram.getVoltaje());
        dto.setActivo(ram.getActivo());
        return dto;
    }
}

package msvc.msvc_ram.Service;

import msvc.msvc_ram.Exception.RamException;
import msvc.msvc_ram.Model.Ram;
import msvc.msvc_ram.Repository.RamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RamServiceImpl implements RamService {

    private final RamRepository repo;

    public RamServiceImpl(RamRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Ram> findAll() {
        return repo.findAll();
    }

    @Override
    public Ram findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RamException("RAM no encontrada"));
    }

    @Override
    public Ram save(Ram ram) {
        return repo.save(ram);
    }

    @Override
    public Ram updateById(Long id, Ram ram) {
        Ram actual = findById(id);
        actual.setTipoDdr(ram.getTipoDdr());
        actual.setCapacidadGb(ram.getCapacidadGb());
        actual.setFrecuenciaMhz(ram.getFrecuenciaMhz());
        actual.setLatenciaCl(ram.getLatenciaCl());
        actual.setModulos(ram.getModulos());
        actual.setVoltaje(ram.getVoltaje());
        actual.setActivo(ram.getActivo());
        return repo.save(actual);
    }

    @Override
    public void deleteById(Long id) {
        Ram actual = findById(id);
        repo.deleteById(actual.getComponenteId());
    }
}

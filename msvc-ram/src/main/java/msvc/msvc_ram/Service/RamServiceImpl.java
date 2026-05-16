package msvc.msvc_ram.Service;

import msvc.msvc_ram.Dto.RamDTO;
import msvc.msvc_ram.Exception.RamException;
import msvc.msvc_ram.Model.Ram;
import msvc.msvc_ram.Repository.RamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RamServiceImpl implements RamService{

    private final RamRepository repo;

    public RamServiceImpl(RamRepository r){ this.repo=r; }

    public Ram create(RamDTO d){

        if(d.getCapacidadGb()<=0)
            throw new RamException("Capacidad inválida");

        if(d.getFrecuenciaMhz()<=0)
            throw new RamException("Frecuencia inválida");

        Ram r=new Ram();
        r.setComponenteId(d.getComponenteId());
        r.setDdr(d.getTipoDdr());
        r.setCapacidadGb(d.getCapacidadGb());
        r.setFrecuenciaMhz(d.getFrecuenciaMhz());
        r.setLatenciaCl(d.getLatenciaCl());
        r.setModulos(d.getModulos());
        r.setVoltaje(d.getVoltaje());

        return repo.save(r);
    }

    public List<Ram> getAll(){ return repo.findAll(); }

    public Ram getById(Long id){
        return repo.findById(id)
                .orElseThrow(()->new RamException("RAM no encontrada"));
    }

    public Ram update(Long id,RamDTO d){
        Ram r=getById(id);
        r.setDdr(d.getTipoDdr());
        r.setCapacidadGb(d.getCapacidadGb());
        r.setFrecuenciaMhz(d.getFrecuenciaMhz());
        r.setLatenciaCl(d.getLatenciaCl());
        r.setModulos(d.getModulos());
        r.setVoltaje(d.getVoltaje());
        return repo.save(r);
    }

    public void deactivate(Long id){
        Ram r=getById(id);
        r.setActivo(false);
        repo.save(r);
    }
}
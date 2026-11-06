package msvc.motherboard.Service;

import msvc.motherboard.Dto.MotherboardDTO;
import msvc.motherboard.Exception.MotherboardException;
import msvc.motherboard.Model.Motherboard;
import msvc.motherboard.Repository.MotherboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotherboardServiceImpl implements MotherboardService {
    private final MotherboardRepository repo;

    public MotherboardServiceImpl(MotherboardRepository r){
        this.repo=r;
    }

    public Motherboard create(MotherboardDTO d){
        Motherboard m=new Motherboard();
        m.setModel(d.getModel());
        m.setSocket(d.getSocket());
        m.setRamType(d.getRamType());
        m.setRamSlots(d.getRamSlots());
        return repo.save(m);
    }

    public List<Motherboard> getAll(){
        return repo.findAll();
    }

    public Motherboard getById(Long id){
        return repo.findById(id)
                .orElseThrow(()->new MotherboardException("Motherboard no encontrada con id: " + id));
    }

    public Motherboard update(Long id, MotherboardDTO d){
        Motherboard m=getById(id);
        m.setModel(d.getModel());
        m.setSocket(d.getSocket());
        m.setRamType(d.getRamType());
        m.setRamSlots(d.getRamSlots());
        return repo.save(m);
    }

    public void delete(Long id){
        repo.deleteById(id);
    }
}

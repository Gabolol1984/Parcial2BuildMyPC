package msvc.powersupply.Service;

import msvc.powersupply.Exception.psuException;
import msvc.powersupply.Repository.psuRepository;
import msvc.powersupply.Model.powerSupply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class psuServicelmpl implements psuService {

    @Autowired
    private psuRepository psuRepository;

    @Transactional
    @Override
    public powerSupply save(powerSupply powerSupply) {
        if (this.psuRepository.findByComponenteId(powerSupply.getComponenteName()).isPresent()) {
            throw new psuException("El componente ya existe en la base de datos");
        }
        return this.psuRepository.save(powerSupply);
    }

    @Transactional(readOnly = true)
    @Override
    public List<powerSupply> getAll(){
        return this.psuRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public powerSupply getById(Long id) {
        return this.psuRepository.findById(id).orElseThrow(
                () -> new psuException("psu con el id: "+ id + "no existe")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public powerSupply updateById(Long id, powerSupply powerSupply) {
        return this.psuRepository.findById(id).map(element->{
            element.setPotenciaWatts(powerSupply.getPotenciaWatts());
            element.setCertificacion(powerSupply.getCertificacion());
            element.setModular(powerSupply.getModular());
            element.setConectoresPcie(powerSupply.getConectoresPcie());
            return this.psuRepository.save(element);
        }).orElseThrow(()->new psuException("psu con el id: "+ id + "no existe"));
    }

    @Override
    public void deleteById(Long id) {

        this.psuRepository.deleteById(id);

    }
}

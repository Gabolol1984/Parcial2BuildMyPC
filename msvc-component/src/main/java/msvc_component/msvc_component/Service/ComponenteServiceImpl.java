package msvc_component.msvc_component.Service;

import msvc_component.msvc_component.Exceptions.ComponenteException;
import msvc_component.msvc_component.Model.Componente;
import msvc_component.msvc_component.Repository.ComponenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComponenteServiceImpl implements ComponenteService {
    @Autowired
    private ComponenteRepository componenteRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Componente> findAll() {
        return this.componenteRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Componente findById(Long id) {
        return this.componenteRepository.findById(id).orElseThrow(
                () -> new ComponenteException("Componente con id: " + id + " no encontrado")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Componente findByTipo(String tipo) {

        return this.componenteRepository.findByTipo(tipo)
                .orElseThrow(
                        () -> new ComponenteException(
                                "No existen componentes del tipo: " + tipo
                        )
                );
    }

    @Transactional
    @Override
    public Componente save(Componente componente) {
        // Validación de precio
        if (componente.getPrecioBase() <= 0) {
            throw new ComponenteException("El precio debe ser mayor a cero");
        }
        return this.componenteRepository.save(componente);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        // Primero verificamos si existe para lanzar la excepción si no
        Componente componente = this.findById(id);
        this.componenteRepository.deleteById(componente.getComponenteId());
    }

    @Transactional
    @Override
    public Componente updateById(Long id, Componente componente) {
        return this.componenteRepository.findById(id).map(element -> {
            element.setMarca(componente.getMarca());
            element.setModelo(componente.getModelo());
            element.setPrecioBase(componente.getPrecioBase());
            element.setTipo(componente.getTipo());
            element.setEstado(componente.getEstado());
            element.setDescripcion(componente.getDescripcion());
            return this.componenteRepository.save(element);
        }).orElseThrow(
                () -> new ComponenteException("El componente con id: " + id + " no existe")
        );
    }
}

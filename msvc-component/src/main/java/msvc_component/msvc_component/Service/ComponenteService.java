package msvc_component.msvc_component.Service;

import msvc_component.msvc_component.Model.Componente;

import java.util.List;

public interface ComponenteService {
    List<Componente> findAll();
    Componente findById(Long id);
    Componente findByTipo(String tipo);
    Componente save(Componente componente);
    void deleteById(Long id);
    Componente updateById(Long id, Componente componente);
}

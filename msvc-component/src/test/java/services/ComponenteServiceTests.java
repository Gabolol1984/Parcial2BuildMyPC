package services;



import msvc_component.msvc_component.Exceptions.ComponenteException;
import msvc_component.msvc_component.Model.Componente;
import msvc_component.msvc_component.Repository.ComponenteRepository;
import msvc_component.msvc_component.Service.ComponenteServiceImpl;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComponenteServiceTest {

    @Mock
    private ComponenteRepository componenteRepository;

    @InjectMocks
    private ComponenteServiceImpl componenteService;

    private Faker faker;
    private Componente componentePrueba;

    @BeforeEach
    void setUp() {

        faker = new Faker();

        componentePrueba = crearComponenteFake();
    }

    private Componente crearComponenteFake() {

        Componente componente = new Componente();

        componente.setComponenteId(
                faker.number().numberBetween(1L, 1000L)
        );

        componente.setMarca(
                faker.options().option(
                        "Intel",
                        "AMD",
                        "NVIDIA",
                        "Corsair",
                        "Kingston",
                        "MSI",
                        "ASUS",
                        "Gigabyte"
                )
        );

        componente.setModelo(
                faker.regexify("[A-Z]{3}-[0-9]{4}")
        );

        componente.setPrecioBase(
                faker.number().numberBetween(
                        50000d,
                        2500000d
                )
        );

        componente.setTipo(
                faker.options().option(
                        "CPU",
                        "GPU",
                        "RAM",
                        "MOTHERBOARD",
                        "PSU"
                )
        );

        componente.setEstado(
                faker.options().option(
                        "ACTIVO",
                        "INACTIVO"
                )
        );

        componente.setDescripcion(
                faker.lorem().sentence()
        );

        return componente;
    }

    @Test
    @DisplayName("Debe listar todos los componentes")
    void shouldFindAllComponentes() {

        when(componenteRepository.findAll())
                .thenReturn(List.of(componentePrueba));

        List<Componente> result =
                componenteService.findAll();

        assertThat(result).hasSize(1);

        assertThat(result.get(0).getComponenteId())
                .isEqualTo(componentePrueba.getComponenteId());

        verify(componenteRepository, times(1))
                .findAll();
    }

    @Test
    @DisplayName("Debe buscar componente por ID")
    void shouldFindComponenteById() {

        Long id = componentePrueba.getComponenteId();

        when(componenteRepository.findById(id))
                .thenReturn(Optional.of(componentePrueba));

        Componente result =
                componenteService.findById(id);

        assertThat(result).isNotNull();

        assertThat(result.getComponenteId())
                .isEqualTo(id);

        verify(componenteRepository, times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el componente no existe")
    void shouldThrowWhenComponenteNotFound() {

        Long id = 9999L;

        when(componenteRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> componenteService.findById(id)
        )
                .isInstanceOf(ComponenteException.class)
                .hasMessage(
                        "Componente con id: " + id + " no encontrado"
                );

        verify(componenteRepository, times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Debe buscar componente por tipo")
    void shouldFindComponenteByTipo() {

        String tipo = "CPU";

        Componente cpu = crearComponenteFake();
        cpu.setTipo(tipo);

        when(componenteRepository.findByTipo(tipo))
                .thenReturn(Optional.of(cpu));

        Componente result =
                componenteService.findByTipo(tipo);

        assertThat(result).isNotNull();

        assertThat(result.getTipo())
                .isEqualTo(tipo);

        verify(componenteRepository, times(1))
                .findByTipo(tipo);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando no existen componentes del tipo solicitado")
    void shouldThrowWhenTipoNotFound() {

        String tipo = "CPU";

        when(componenteRepository.findByTipo(tipo))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> componenteService.findByTipo(tipo)
        )
                .isInstanceOf(ComponenteException.class)
                .hasMessage(
                        "No existen componentes del tipo: " + tipo
                );

        verify(componenteRepository, times(1))
                .findByTipo(tipo);
    }

    @Test
    @DisplayName("Debe guardar un componente")
    void shouldSaveComponente() {

        when(componenteRepository.save(any(Componente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Componente result =
                componenteService.save(componentePrueba);

        assertThat(result).isNotNull();

        assertThat(result.getMarca())
                .isEqualTo(componentePrueba.getMarca());

        verify(componenteRepository, times(1))
                .save(any(Componente.class));
    }

    @Test
    @DisplayName("Debe impedir guardar un componente con precio menor o igual a cero")
    void shouldThrowWhenPrecioIsInvalid() {

        componentePrueba.setPrecioBase(0d);

        assertThatThrownBy(
                () -> componenteService.save(componentePrueba)
        )
                .isInstanceOf(ComponenteException.class)
                .hasMessage("El precio debe ser mayor a cero");

        verify(componenteRepository, never())
                .save(any(Componente.class));
    }

    @Test
    @DisplayName("Debe actualizar un componente")
    void shouldUpdateComponente() {

        Long id = componentePrueba.getComponenteId();

        Componente cambios = crearComponenteFake();

        when(componenteRepository.findById(id))
                .thenReturn(Optional.of(componentePrueba));

        when(componenteRepository.save(any(Componente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Componente result =
                componenteService.updateById(id, cambios);

        assertThat(result.getMarca())
                .isEqualTo(cambios.getMarca());

        assertThat(result.getModelo())
                .isEqualTo(cambios.getModelo());

        assertThat(result.getPrecioBase())
                .isEqualTo(cambios.getPrecioBase());

        assertThat(result.getTipo())
                .isEqualTo(cambios.getTipo());

        verify(componenteRepository, times(1))
                .findById(id);

        verify(componenteRepository, times(1))
                .save(any(Componente.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar un componente inexistente")
    void shouldThrowWhenUpdatingNonExistingComponente() {

        Long id = 9999L;

        when(componenteRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> componenteService.updateById(id, crearComponenteFake())
        )
                .isInstanceOf(ComponenteException.class)
                .hasMessage(
                        "El componente con id: " + id + " no existe"
                );

        verify(componenteRepository, times(1))
                .findById(id);

        verify(componenteRepository, never())
                .save(any(Componente.class));
    }

    @Test
    @DisplayName("Debe eliminar un componente")
    void shouldDeleteComponente() {

        Long id = componentePrueba.getComponenteId();

        when(componenteRepository.findById(id))
                .thenReturn(Optional.of(componentePrueba));

        componenteService.deleteById(id);

        verify(componenteRepository, times(1))
                .findById(id);

        verify(componenteRepository, times(1))
                .deleteById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar un componente inexistente")
    void shouldThrowWhenDeletingNonExistingComponente() {

        Long id = 9999L;

        when(componenteRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> componenteService.deleteById(id)
        )
                .isInstanceOf(ComponenteException.class)
                .hasMessage(
                        "Componente con id: " + id + " no encontrado"
                );

        verify(componenteRepository, times(1))
                .findById(id);

        verify(componenteRepository, never())
                .deleteById(anyLong());
    }
}
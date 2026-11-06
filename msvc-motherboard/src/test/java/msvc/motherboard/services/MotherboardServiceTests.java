package msvc.motherboard.services;

import msvc.motherboard.Exception.MotherboardException;
import msvc.motherboard.Model.Motherboard;
import msvc.motherboard.Repository.MotherboardRepository;
import msvc.motherboard.Service.MotherboardServiceImpl;
import msvc.motherboard.Dto.MotherboardDTO;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MotherboardServiceTests {
    @Mock
    private MotherboardRepository motherboardRepository;

    // @InjectMocks: crea el servicio real y le inyecta los @Mock de arriba.
    @InjectMocks
    private MotherboardServiceImpl motherboardService;

    private Motherboard motherboardPrueba;
    private MotherboardDTO motherboardDTOPrueba;
    private List<Motherboard> motherboardList = new ArrayList<>();

    // @BeforeEach se ejecuta antes de CADA test para dejar los datos en un estado conocido.
    @BeforeEach
    public void setUp() {
        this.motherboardPrueba = new Motherboard();
        this.motherboardPrueba.setId(1L);
        this.motherboardPrueba.setModel("ROG STRIX B550-F");
        this.motherboardPrueba.setSocket("AM4");
        this.motherboardPrueba.setRamType("DDR4");
        this.motherboardPrueba.setRamSlots(4);

        this.motherboardDTOPrueba = new MotherboardDTO();
        this.motherboardDTOPrueba.setModel("ROG STRIX B550-F");
        this.motherboardDTOPrueba.setSocket("AM4");
        this.motherboardDTOPrueba.setRamType("DDR4");
        this.motherboardDTOPrueba.setRamSlots(4);

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 100; i++) {
            Motherboard mb = new Motherboard();
            mb.setId((long) (i + 2));
            mb.setModel(faker.options().option("Prime ", "TUF Gaming ", "MPG ") + faker.expression("#{regexify '[A-Z0-9]{5}'}"));
            mb.setSocket(faker.options().option("AM4", "AM5", "LGA1700"));
            mb.setRamType(faker.options().option("DDR4", "DDR5"));
            mb.setRamSlots(faker.options().option(2, 4));

            this.motherboardList.add(mb);
        }
    }

    // Patron AAA: Arrange (preparar) -> Act (ejecutar) -> Assert (verificar).
    @Test
    @DisplayName("Debe listar todas las motherboards")
    public void shouldListAllMotherboards() {
        // Arrange: definimos que devuelve el mock cuando se llame findAll().
        List<Motherboard> motherboards = this.motherboardList;
        motherboards.add(this.motherboardPrueba);
        when(this.motherboardRepository.findAll()).thenReturn(motherboards);

        // ACT: llamamos al metodo real del servicio.
        List<Motherboard> result = this.motherboardService.getAll();

        // ASSERT: comprobamos el resultado y que el repo se uso 1 vez.
        assertThat(result).hasSize(101);
        assertThat(result).contains(motherboardPrueba);
        verify(motherboardRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una motherboard por su id")
    public void shouldFindMotherboardById() {
        // Arrange
        Long id = 1L;
        when(this.motherboardRepository.findById(id)).thenReturn(Optional.of(this.motherboardPrueba));

        // Act
        Motherboard result = this.motherboardService.getById(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getModel()).isEqualTo("ROG STRIX B550-F");
        assertThat(result.getSocket()).isEqualTo("AM4");
        verify(motherboardRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe buscar una motherboard con un id inexistente")
    public void shouldNotFindMotherboardById() {
        Long id = 9999L;
        // El repo "no encuentra" nada (Optional vacio), asi el servicio debe lanzar excepcion.
        when(this.motherboardRepository.findById(id)).thenReturn(Optional.empty());

        // assertThatThrownBy verifica que se lance la excepcion esperada y con el mensaje correcto.
        assertThatThrownBy(() -> {
            this.motherboardService.getById(id);
        }).isInstanceOf(MotherboardException.class)
                .hasMessage("Motherboard no encontrada con id: " + id);
        verify(motherboardRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe guardar una motherboard nueva")
    public void shouldSaveMotherboard() {
        // Arrange
        when(this.motherboardRepository.save(any(Motherboard.class))).thenReturn(this.motherboardPrueba);

        // Act
        Motherboard result = this.motherboardService.create(this.motherboardDTOPrueba);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getModel()).isEqualTo("ROG STRIX B550-F");
        verify(motherboardRepository, times(1)).save(any(Motherboard.class));
    }

    @Test
    @DisplayName("Debe actualizar una motherboard existente")
    public void shouldUpdateMotherboardById() {
        // Arrange
        Long id = 1L;
        MotherboardDTO cambios = new MotherboardDTO();
        cambios.setModel("MEG X670E");
        cambios.setSocket("AM5");
        cambios.setRamType("DDR5");
        cambios.setRamSlots(4);

        when(this.motherboardRepository.findById(id)).thenReturn(Optional.of(this.motherboardPrueba));
        when(this.motherboardRepository.save(any(Motherboard.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Motherboard result = this.motherboardService.update(id, cambios);

        // Assert
        assertThat(result.getModel()).isEqualTo("MEG X670E");
        assertThat(result.getSocket()).isEqualTo("AM5");
        assertThat(result.getRamType()).isEqualTo("DDR5");
        verify(motherboardRepository, times(1)).findById(id);
        verify(motherboardRepository, times(1)).save(this.motherboardPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepcion al actualizar una motherboard inexistente")
    public void shouldNotUpdateMotherboardWhenNotExists() {
        // Arrange
        Long id = 9999L;
        when(this.motherboardRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> {
            this.motherboardService.update(id, this.motherboardDTOPrueba);
        }).isInstanceOf(MotherboardException.class)
                .hasMessage("Motherboard no encontrada con id: " + id);
        verify(motherboardRepository, times(1)).findById(id);
        verify(motherboardRepository, never()).save(any(Motherboard.class));
    }

    @Test
    @DisplayName("Debe eliminar una motherboard por id")
    public void shouldDeleteMotherboardById() {
        // Arrange
        Long id = 1L;

        // Act
        this.motherboardService.delete(id);

        // Assert
        verify(motherboardRepository, times(1)).deleteById(id);
    }
}

package msvc.msvc_ram.services;


import msvc.msvc_ram.Dto.RamDTO;
import msvc.msvc_ram.Exception.RamException;
import msvc.msvc_ram.Model.Ram;
import msvc.msvc_ram.Repository.RamRepository;
import msvc.msvc_ram.Service.RamServiceImpl;
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
public class RamServiceTests {

    @Mock
    private RamRepository ramRepository;

    @InjectMocks
    private RamServiceImpl ramService;

    private Ram ramPrueba;
    private RamDTO ramDTOPrueba;
    private List<Ram> ramList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.ramDTOPrueba = new RamDTO();
        this.ramPrueba = new Ram();
        this.ramPrueba.setComponenteId(1L);
        this.ramPrueba.setTipo("Memoria RAM");
        this.ramPrueba.setMarca("Corsair");
        this.ramPrueba.setModelo("Vengeance LPX");
        this.ramPrueba.setPrecioBase(85.50);
        this.ramPrueba.setEstado("DISPONIBLE");
        this.ramPrueba.setTipoDdr("DDR4");
        this.ramPrueba.setCapacidadGb(16);
        this.ramPrueba.setFrecuenciaMhz(3200);
        this.ramPrueba.setLatenciaCl("CL16");
        this.ramPrueba.setModulos(2);
        this.ramPrueba.setVoltaje(1.35);
        this.ramPrueba.setActivo(true);

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 100; i++) {
            Ram ram = new Ram();
            ram.setComponenteId((long) (i + 2)); // IDs del 2 al 101
            ram.setTipo("Memoria RAM");
            ram.setMarca(faker.commerce().brand());
            ram.setModelo(faker.commerce().productName() + " " + i);
            ram.setPrecioBase(faker.number().randomDouble(2, 40, 300));
            ram.setEstado("DISPONIBLE");
            ram.setTipoDdr(faker.options().option("DDR4", "DDR5"));
            ram.setCapacidadGb(faker.options().option(8, 16, 32, 64));
            ram.setFrecuenciaMhz(faker.options().option(2666, 3200, 3600, 5200));
            ram.setLatenciaCl("CL" + faker.number().numberBetween(14, 22));
            ram.setModulos(faker.options().option(1, 2, 4));
            ram.setVoltaje(1.2 + faker.number().randomDouble(2, 0, 1));
            ram.setActivo(true);

            ramList.add(ram);
        }
        //profe le busque con IA como hacer el print
        System.out.println("==============================================");
        System.out.println("¡Se han generado exitosamente " + ramList.size() + " RAMs con DataFaker!");
        System.out.println("Ejemplo de la primera RAM aleatoria: " + ramList.get(0).getMarca() + " - " + ramList.get(0).getModelo());
        System.out.println("==============================================");
    }

    @Test
    @DisplayName("Debe crear una nueva ram correctamente")
    public void shouldCreateRamSuccessfully() {
        // Arrange: definimos que devuelve el mock cuando se llame findAll().
        when(this.ramRepository.findByModelo(this.ramDTOPrueba.getModelo())).thenReturn(Optional.empty());
        when(this.ramRepository.save(any(Ram.class))).thenReturn(this.ramPrueba);
        // ACT: llamamos al metodo real del servicio.
        RamDTO result = this.ramService.crear(this.ramDTOPrueba);
        // ASSERT: comprobamos el resultado y que el repo se uso 1 vez.
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getModelo()).isEqualTo("Vengeance LPX");
        assertThat(result.getEstado()).isEqualTo("DISPONIBLE");
        assertThat(result.getActivo()).isTrue();
        verify(ramRepository, times(1)).findByModelo(this.ramDTOPrueba.getModelo());
        verify(ramRepository, times(1)).save(any(Ram.class));
    }

    @Test
    @DisplayName("Debe lanzar una excepcion al crear una RAM con modelo duplicado")
    public void shouldThrowExceptionWhenModelAlreadyExists() {
        // Arrange
        when(ramRepository.findByModelo(ramDTOPrueba.getModelo())).thenReturn(Optional.of(ramPrueba));

        // Act, Assert
        assertThatThrownBy(() -> ramService.crear(ramDTOPrueba))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ya existe una RAM con el modelo: " + ramDTOPrueba.getModelo());

        verify(ramRepository, times(1)).findByModelo(ramDTOPrueba.getModelo());
        verify(ramRepository, never()).save(any(Ram.class));
    }

    @Test
    @DisplayName("Debe listar todas las memorias RAM")
    public void shouldListAllRams() {
        // Arrange: definimos que devuelve el mock cuando se llame findAll().
        List<Ram> todasLasRams = new ArrayList<>(ramList);
        todasLasRams.add(ramPrueba);
        when(ramRepository.findAll()).thenReturn(todasLasRams);

        // ACT: llamamos al metodo real del servicio.
        List<RamDTO> result = ramService.listarTodos();

        // ASSERT: comprobamos el resultado y que el repo se uso 1 vez.
        assertThat(result).hasSize(101);
        verify(ramRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("Debe buscar una RAM por su ID con éxito")
    public void shouldFindRamById() {
        // Arrange
        Long id = 1L;
        when(ramRepository.findById(id)).thenReturn(Optional.of(ramPrueba));

        // Act
        RamDTO result = ramService.buscarPorId(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getModelo()).isEqualTo("Vengeance LPX");
        verify(ramRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe lanzar Exception al buscar un ID que no existe")
    public void shouldThrowRamExceptionWhenIdNotFound() {
        // Arrange
        Long id = 999L;
        when(ramRepository.findById(id)).thenReturn(Optional.empty());

        // Act,Assert
        assertThatThrownBy(() -> ramService.buscarPorId(id))
                .isInstanceOf(RamException.class)
                .hasMessage("RAM no encontrada con ID: " + id);

        verify(ramRepository, times(1)).findById(id);
    }
    @Test
    @DisplayName("Debe actualizar los datos de una RAM existente")
    public void shouldUpdateRamSuccessfully() {
        // Arrange
        Long id = 1L;
        RamDTO dtoCambios = new RamDTO();
        dtoCambios.setModelo("Vengeance RGB Pro");
        dtoCambios.setPrecioBase(99.99);
        dtoCambios.setCapacidadGb(32);

        when(ramRepository.findById(id)).thenReturn(Optional.of(ramPrueba));
        // Respondemos con el mismo objeto modificado por el método
        when(ramRepository.save(any(Ram.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RamDTO result = ramService.actualizar(id, dtoCambios);

        // Assert
        assertThat(result.getModelo()).isEqualTo("Vengeance RGB Pro");
        assertThat(result.getPrecioBase()).isEqualTo(99.99);
        assertThat(result.getCapacidadGb()).isEqualTo(32);
        verify(ramRepository, times(1)).findById(id);
        verify(ramRepository, times(1)).save(ramPrueba);
    }
    @Test
    @DisplayName("Debe eliminar una RAM por ID")
    public void shouldDeleteRamPermanently() {
        // Arrange
        Long id = 1L;
        when(ramRepository.existsById(id)).thenReturn(true);

        // Act
        ramService.eliminarPermanente(id);

        // Assert
        verify(ramRepository, times(1)).existsById(id);
        verify(ramRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Debe lanzar una RamException al intentar eliminar un ID que no existe")
    public void shouldThrowExceptionWhenDeletingNonExistentRam() {
        // Arrange
        Long id = 999L;
        when(ramRepository.existsById(id)).thenReturn(false);

        // Act,Assert
        assertThatThrownBy(() -> ramService.eliminarPermanente(id))
                .isInstanceOf(RamException.class)
                .hasMessage("RAM no encontrada con ID: " + id);

        verify(ramRepository, times(1)).existsById(id);
        verify(ramRepository, never()).deleteById(anyLong());
    }
}

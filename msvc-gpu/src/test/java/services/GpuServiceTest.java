package services;


import msvc.gpu.Dto.GpuDTO;
import msvc.gpu.Exception.GpuException;
import msvc.gpu.Model.Gpu;
import msvc.gpu.Repository.GpuRepository;
import msvc.gpu.Service.GpuServiceImpl;
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
class GpuServiceTest {

    @Mock
    private GpuRepository repo;

    @InjectMocks
    private GpuServiceImpl service;

    private Faker faker;
    private Gpu gpuPrueba;
    private GpuDTO gpuDTO;

    @BeforeEach
    void setUp() {

        faker = new Faker();

        gpuPrueba = crearGpuFake();

        gpuDTO = new GpuDTO();
        gpuDTO.setMarca(gpuPrueba.getMarca());
        gpuDTO.setModelo(gpuPrueba.getModelo());
        gpuDTO.setPrecioBase(gpuPrueba.getPrecioBase());
        gpuDTO.setTipoMemoria(gpuPrueba.getTipoMemoria());
        gpuDTO.setMemoriaGb(gpuPrueba.getMemoriaGb());
        gpuDTO.setConsumoW(gpuPrueba.getConsumoW());
        gpuDTO.setDescripcion(gpuPrueba.getDescripcion());
    }

    private Gpu crearGpuFake() {

        Gpu gpu = new Gpu();

        gpu.setComponenteId(faker.number().randomNumber());

        gpu.setMarca(
                faker.options().option(
                        "NVIDIA",
                        "AMD",
                        "Intel"
                )
        );

        gpu.setModelo(
                "GPU-" + faker.number().digits(4)
        );

        gpu.setPrecioBase(
                faker.number().randomDouble(2, 50000, 150000)
        );

        gpu.setTipoMemoria(
                faker.options().option(
                        "GDDR6",
                        "GDDR6X",
                        "HBM3"
                )
        );

        gpu.setMemoriaGb(
                faker.options().option(
                        8,
                        12,
                        16,
                        24
                )
        );

        gpu.setConsumoW(
                faker.number().numberBetween(100, 450)
        );

        gpu.setDescripcion(
                faker.lorem().sentence()
        );

        return gpu;
    }

    @Test
    @DisplayName("Debe listar todas las GPUs")
    void shouldFindAllGpus() {

        when(repo.findAll())
                .thenReturn(List.of(gpuPrueba));

        List<Gpu> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMarca())
                .isEqualTo(gpuPrueba.getMarca());

        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una GPU por ID")
    void shouldFindGpuById() {

        Long id = gpuPrueba.getComponenteId();

        when(repo.findById(id))
                .thenReturn(Optional.of(gpuPrueba));

        Gpu result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getComponenteId())
                .isEqualTo(id);

        verify(repo, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la GPU no existe")
    void shouldThrowWhenGpuNotFound() {

        Long id = 999L;

        when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(GpuException.class)
                .hasMessage("GPU no encontrada");

        verify(repo, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe crear una GPU")
    void shouldCreateGpu() {

        when(repo.save(any(Gpu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Gpu result = service.create(gpuDTO);

        assertThat(result).isNotNull();
        assertThat(result.getMarca())
                .isEqualTo(gpuDTO.getMarca());

        assertThat(result.getModelo())
                .isEqualTo(gpuDTO.getModelo());

        verify(repo, times(1))
                .save(any(Gpu.class));
    }

    @Test
    @DisplayName("Debe actualizar una GPU")
    void shouldUpdateGpu() {

        Long id = gpuPrueba.getComponenteId();

        GpuDTO cambios = new GpuDTO();

        cambios.setMarca("AMD");
        cambios.setModelo("RX-" + faker.number().digits(4));
        cambios.setPrecioBase(
                faker.number().randomDouble(2, 70000, 180000)
        );
        cambios.setTipoMemoria("GDDR6");
        cambios.setMemoriaGb(16);
        cambios.setConsumoW(300);
        cambios.setDescripcion(
                faker.lorem().sentence()
        );

        when(repo.findById(id))
                .thenReturn(Optional.of(gpuPrueba));

        when(repo.save(any(Gpu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Gpu result = service.update(id, cambios);

        assertThat(result.getMarca())
                .isEqualTo(cambios.getMarca());

        assertThat(result.getModelo())
                .isEqualTo(cambios.getModelo());

        assertThat(result.getMemoriaGb())
                .isEqualTo(cambios.getMemoriaGb());

        verify(repo, times(1))
                .findById(id);

        verify(repo, times(1))
                .save(any(Gpu.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar GPU inexistente")
    void shouldNotUpdateGpuWhenNotExists() {

        Long id = 999L;

        when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, gpuDTO))
                .isInstanceOf(GpuException.class)
                .hasMessage("GPU no encontrada");

        verify(repo, times(1))
                .findById(id);

        verify(repo, never())
                .save(any(Gpu.class));
    }

    @Test
    @DisplayName("Debe eliminar una GPU")
    void shouldDeleteGpu() {

        Long id = gpuPrueba.getComponenteId();

        service.deactivate(id);

        verify(repo, times(1))
                .deleteById(id);
    }
}
package services;


import msvc.gpu.Dto.GpuDTO;
import msvc.gpu.Exception.GpuException;
import msvc.gpu.Model.Gpu;
import msvc.gpu.Repository.GpuRepository;
import msvc.gpu.Service.GpuServiceImpl;
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

    private Gpu gpuPrueba;
    private GpuDTO gpuDTO;

    @BeforeEach
    void setUp() {

        gpuPrueba = new Gpu();
        gpuPrueba.setComponenteId(1L);
        gpuPrueba.setMarca("NVIDIA");
        gpuPrueba.setModelo("RTX 4070");
        gpuPrueba.setPrecioBase (65000d);
        gpuPrueba.setTipoMemoria("GDDR6X");
        gpuPrueba.setMemoriaGb(12);
        gpuPrueba.setConsumoW(200);
        gpuPrueba.setDescripcion("GPU de prueba");

        gpuDTO = new GpuDTO();
        gpuDTO.setMarca("NVIDIA");
        gpuDTO.setModelo("RTX 4070");
        gpuDTO.setPrecioBase (65000d);
        gpuDTO.setTipoMemoria("GDDR6X");
        gpuDTO.setMemoriaGb(12);
        gpuDTO.setConsumoW(200);
        gpuDTO.setDescripcion("GPU de prueba");
    }

    @Test
    @DisplayName("Debe listar todas las GPUs")
    void shouldFindAllGpus() {

        when(repo.findAll())
                .thenReturn(List.of(gpuPrueba));

        List<Gpu> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMarca()).isEqualTo("NVIDIA");

        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una GPU por ID")
    void shouldFindGpuById() {

        Long id = 1L;

        when(repo.findById(id))
                .thenReturn(Optional.of(gpuPrueba));

        Gpu result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getComponenteId()).isEqualTo(id);

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
        assertThat(result.getMarca()).isEqualTo("NVIDIA");
        assertThat(result.getModelo()).isEqualTo("RTX 4070");

        verify(repo, times(1)).save(any(Gpu.class));
    }

    @Test
    @DisplayName("Debe actualizar una GPU")
    void shouldUpdateGpu() {

        Long id = 1L;

        GpuDTO cambios = new GpuDTO();
        cambios.setMarca("AMD");
        cambios.setModelo("RX 9070 XT");
        cambios.setPrecioBase (7000d) ;
        cambios.setTipoMemoria("GDDR6");
        cambios.setMemoriaGb(16);
        cambios.setConsumoW(250);
        cambios.setDescripcion("Actualizada");

        when(repo.findById(id))
                .thenReturn(Optional.of(gpuPrueba));

        when(repo.save(any(Gpu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Gpu result = service.update(id, cambios);

        assertThat(result.getMarca()).isEqualTo("AMD");
        assertThat(result.getModelo()).isEqualTo("RX 9070 XT");
        assertThat(result.getMemoriaGb()).isEqualTo(16);

        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(any(Gpu.class));
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

        verify(repo, times(1)).findById(id);
        verify(repo, never()).save(any(Gpu.class));
    }

    @Test
    @DisplayName("Debe eliminar una GPU")
    void shouldDeleteGpu() {

        Long id = 1L;

        service.deactivate(id);

        verify(repo, times(1)).deleteById(id);
    }
}
package services;


import com.buildmypc.msvc_build.client.*;
import com.buildmypc.msvc_build.dto.*;
import com.buildmypc.msvc_build.exception.buildException;
import com.buildmypc.msvc_build.model.build;
import com.buildmypc.msvc_build.model.build.EstadoBuild;
import com.buildmypc.msvc_build.repository.buildRepository;
import com.buildmypc.msvc_build.service.buildServicelmpl;
import feign.FeignException;
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
class BuildServiceTest {

    @Mock
    private buildRepository buildRepository;

    @Mock
    private usuarioClient usuarioClient;

    @Mock
    private cpuClient cpuClient;

    @Mock
    private gpuClient gpuClient;

    @Mock
    private MotherboardClient motherboardClient;

    @Mock
    private ramClient ramClient;

    @Mock
    private powersupplyClient powersupplyClient;

    @InjectMocks
    private buildServicelmpl service;

    private Faker faker;
    private build buildPrueba;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        buildPrueba = crearBuildFake();
    }

    private build crearBuildFake() {

        build b = new build();

        b.setId(faker.number().randomNumber());

        b.setUsuarioId(
                faker.number().numberBetween(1L, 100L)
        );

        b.setCpuId(
                faker.number().numberBetween(1L, 100L)
        );

        b.setGpuId(
                faker.number().numberBetween(1L, 100L)
        );

        b.setMotherboardId(
                faker.number().numberBetween(1L, 100L)
        );

        b.setRamId(
                faker.number().numberBetween(1L, 100L)
        );

        b.setFuenteId(
                faker.number().numberBetween(1L, 100L)
        );

        b.setEstado(build.EstadoBuild.BORRADOR);

        return b;
    }

    @Test
    @DisplayName("Debe listar todas las builds")
    void shouldFindAllBuilds() {

        when(buildRepository.findAll())
                .thenReturn(List.of(buildPrueba));

        when(usuarioClient.getById(anyLong()))
                .thenReturn(new usuarioDTO());

        when(cpuClient.getById(anyLong()))
                .thenReturn(new cpuDTO());

        when(gpuClient.getById(anyLong()))
                .thenReturn(new GpuDTO());

        List<buildDto> result = service.findAll();

        assertThat(result).hasSize(1);

        verify(buildRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar build por id")
    void shouldFindBuildById() {

        Long id = buildPrueba.getId();

        when(buildRepository.findById(id))
                .thenReturn(Optional.of(buildPrueba));

        build result = service.getById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);

        verify(buildRepository, times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción si build no existe")
    void shouldThrowWhenBuildNotFound() {

        Long id = 999L;

        when(buildRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.getById(id))
                .isInstanceOf(buildException.class);

        verify(buildRepository, times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Debe guardar build correctamente")
    void shouldSaveBuild() {

        usuarioDTO usuario = new usuarioDTO();
        usuario.setId(buildPrueba.getUsuarioId());

        when(usuarioClient.getById(anyLong()))
                .thenReturn(usuario);

        when(cpuClient.getById(anyLong()))
                .thenReturn(new cpuDTO());

        when(gpuClient.getById(anyLong()))
                .thenReturn(new GpuDTO());

        when(motherboardClient.getById(anyLong()))
                .thenReturn(new motherboardDTO());

        when(ramClient.getById(anyLong()))
                .thenReturn(new ramDTO());

        when(powersupplyClient.getById(anyLong()))
                .thenReturn(new powersupplyDTO());

        when(buildRepository.save(any(build.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        build result = service.save(buildPrueba);

        assertThat(result).isNotNull();

        verify(buildRepository, times(1))
                .save(buildPrueba);
    }

    @Test
    @DisplayName("Debe actualizar build")
    void shouldUpdateBuild() {

        Long id = buildPrueba.getId();

        build cambios = crearBuildFake();

        usuarioDTO usuario = new usuarioDTO();
        usuario.setId(cambios.getUsuarioId());

        when(buildRepository.findById(id))
                .thenReturn(Optional.of(buildPrueba));

        when(usuarioClient.getById(anyLong()))
                .thenReturn(usuario);

        when(buildRepository.save(any(build.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        build result = service.updateById(cambios, id);

        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId())
                .isEqualTo(cambios.getUsuarioId());

        verify(buildRepository, times(1))
                .findById(id);

        verify(buildRepository, times(1))
                .save(any(build.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar build inexistente")
    void shouldNotUpdateBuildWhenNotExists() {

        Long id = 999L;

        when(buildRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.updateById(crearBuildFake(), id))
                .isInstanceOf(buildException.class);

        verify(buildRepository, times(1))
                .findById(id);

        verify(buildRepository, never())
                .save(any(build.class));
    }

    @Test
    @DisplayName("Debe eliminar build")
    void shouldDeleteBuild() {

        Long id = buildPrueba.getId();

        service.deleteById(id);

        verify(buildRepository, times(1))
                .deleteById(id);
    }
}


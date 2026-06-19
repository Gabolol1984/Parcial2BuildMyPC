package com.service.services;


import com.buildmypc.msvc.cpu.model.cpu;
import com.buildmypc.msvc.cpu.exception.cpuException;
import com.buildmypc.msvc.cpu.repository.cpuRepository;
import com.buildmypc.msvc.cpu.service.cpuServicelmpl;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CpuServiceTest {

    @Mock
    private cpuRepository cpuRepository;

    @InjectMocks
    private cpuServicelmpl cpuService;

    private cpu cpuPrueba;
    private List<cpu> cpuList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        cpuPrueba = new cpu();

        cpuPrueba.setId(1L);
        cpuPrueba.setCpuName("Ryzen 7 7800X3D");
        cpuPrueba.setSocket("AM5");
        cpuPrueba.setNucleos(8);
        cpuPrueba.setHilos(16);
        cpuPrueba.setFrecuenciaBase(4.2);
        cpuPrueba.setTdpWatts(120);
        cpuPrueba.setGeneracion("7000");
        cpuPrueba.setSoportaDdr4(false);
        cpuPrueba.setSoportaDdr5(true);

        Faker faker = new Faker(Locale.of("es", "CL"));

        for(int i=0; i<50; i++){

            cpu cpu = new cpu();

            cpu.setCpuName("CPU-" + faker.number().digits(5));
            cpu.setSocket("AM4");
            cpu.setNucleos(6);
            cpu.setHilos(12);
            cpu.setFrecuenciaBase(3.6);
            cpu.setTdpWatts(65);
            cpu.setGeneracion("5000");
            cpu.setSoportaDdr4(true);
            cpu.setSoportaDdr5(false);

            cpuList.add(cpu);
        }
    }

    @Test
    @DisplayName("Debe listar todos los CPU")
    void shouldGetAllCpu() {

        List<cpu> cpus = cpuList;
        cpus.add(cpuPrueba);

        when(cpuRepository.findAll()).thenReturn(cpus);

        List<cpu> result = cpuService.getAll();

        assertThat(result).hasSize(51);
        assertThat(result).contains(cpuPrueba);

        verify(cpuRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar CPU por ID")
    void shouldFindCpuById() {

        Long id = 1L;

        when(cpuRepository.findById(id))
                .thenReturn(Optional.of(cpuPrueba));

        cpu result = cpuService.getById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);

        verify(cpuRepository, times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar CPU inexistente")
    void shouldNotFindCpuById() {

        Long id = 999L;

        when(cpuRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cpuService.getById(id))
                .isInstanceOf(cpuException.class)
                .hasMessage("cpu con el  id999no existe");

        verify(cpuRepository, times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Debe guardar CPU")
    void shouldSaveCpu() {

        when(cpuRepository.findBycpuName(cpuPrueba.getCpuName()))
                .thenReturn(Optional.empty());

        when(cpuRepository.save(cpuPrueba))
                .thenReturn(cpuPrueba);

        cpu result = cpuService.save(cpuPrueba);

        assertThat(result).isNotNull();
        assertThat(result.getCpuName())
                .isEqualTo("Ryzen 7 7800X3D");

        verify(cpuRepository, times(1))
                .save(cpuPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción al guardar CPU existente")
    void shouldNotSaveExistingCpu() {

        when(cpuRepository.findBycpuName(cpuPrueba.getCpuName()))
                .thenReturn(Optional.of(cpuPrueba));

        assertThatThrownBy(() -> cpuService.save(cpuPrueba))
                .isInstanceOf(cpuException.class)
                .hasMessage("cpu existente");

        verify(cpuRepository, never())
                .save(any(cpu.class));
    }

    @Test
    @DisplayName("Debe actualizar CPU existente")
    void shouldUpdateCpu() {

        Long id = 1L;

        cpu cambios = new cpu();

        cambios.setSocket("AM5");
        cambios.setNucleos(12);
        cambios.setHilos(24);
        cambios.setFrecuenciaBase(4.7);
        cambios.setTdpWatts(170);
        cambios.setGeneracion("9000");
        cambios.setSoportaDdr4(false);
        cambios.setSoportaDdr5(true);

        when(cpuRepository.findById(id))
                .thenReturn(Optional.of(cpuPrueba));

        when(cpuRepository.save(any(cpu.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        cpu result = cpuService.updateById(id, cambios);

        assertThat(result.getNucleos()).isEqualTo(12);
        assertThat(result.getHilos()).isEqualTo(24);
        assertThat(result.getGeneracion()).isEqualTo("9000");

        verify(cpuRepository, times(1))
                .findById(id);

        verify(cpuRepository, times(1))
                .save(cpuPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar CPU inexistente")
    void shouldNotUpdateCpu() {

        Long id = 999L;

        when(cpuRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cpuService.updateById(id, cpuPrueba))
                .isInstanceOf(cpuException.class)
                .hasMessage("cpu con el  id999no existe");

        verify(cpuRepository, times(1))
                .findById(id);

        verify(cpuRepository, never())
                .save(any(cpu.class));
    }

    @Test
    @DisplayName("Debe eliminar CPU")
    void shouldDeleteCpu() {

        Long id = 1L;

        cpuService.delete(id);

        verify(cpuRepository, times(1))
                .deleteById(id);
    }
}
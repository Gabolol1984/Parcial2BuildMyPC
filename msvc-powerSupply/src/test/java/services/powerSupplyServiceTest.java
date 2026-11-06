package services;


import msvc.powersupply.Exception.psuException;
import msvc.powersupply.Model.powerSupply;
import msvc.powersupply.Repository.psuRepository;
import msvc.powersupply.Service.psuServicelmpl;
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
class PsuServiceTest {

    @Mock
    private psuRepository repo;

    @InjectMocks
    private psuServicelmpl service;

    private Faker faker;
    private powerSupply psuPrueba;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        psuPrueba = crearPsuFake();
    }

    private powerSupply crearPsuFake() {

        powerSupply psu = new powerSupply();

        psu.setId(faker.number().randomNumber());

        psu.setComponenteId(
                faker.number().numberBetween(1L, 1000L)
        );

        psu.setComponenteName(
                faker.options().option(
                        "Corsair RM850x",
                        "EVGA SuperNova 750",
                        "Cooler Master MWE",
                        "Seasonic Focus GX",
                        "MSI MAG A850GL"
                )
        );

        psu.setPotenciaWatts(
                faker.options().option(
                        550,
                        650,
                        750,
                        850,
                        1000,
                        1200
                )
        );

        psu.setCertificacion(
                faker.options().option(
                        "Bronze",
                        "Silver",
                        "Gold",
                        "Platinum"
                )
        );

        psu.setModular(
                faker.bool().bool()
        );

        psu.setConectoresPcie(
                faker.number().numberBetween(1, 6)
        );

        psu.setValor(
                faker.number().numberBetween(
                        40000,
                        250000
                )
        );

        return psu;
    }

    @Test
    @DisplayName("Debe listar todas las PSU")
    void shouldGetAllPsu() {

        when(repo.findAll())
                .thenReturn(List.of(psuPrueba));

        List<powerSupply> result = service.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPotenciaWatts())
                .isEqualTo(psuPrueba.getPotenciaWatts());

        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar PSU por ID")
    void shouldGetPsuById() {

        Long id = psuPrueba.getId();

        when(repo.findById(id))
                .thenReturn(Optional.of(psuPrueba));

        powerSupply result = service.getById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);

        verify(repo, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la PSU no existe")
    void shouldThrowWhenPsuNotFound() {

        Long id = 999L;

        when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(psuException.class);

        verify(repo, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe guardar una PSU")
    void shouldSavePsu() {

        when(repo.findByComponenteId(psuPrueba.getComponenteId()))
                .thenReturn(Optional.empty());

        when(repo.save(any(powerSupply.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        powerSupply result = service.save(psuPrueba);

        assertThat(result).isNotNull();
        assertThat(result.getComponenteId())
                .isEqualTo(psuPrueba.getComponenteId());

        verify(repo, times(1))
                .findByComponenteId(psuPrueba.getComponenteId());

        verify(repo, times(1))
                .save(any(powerSupply.class));
    }

    @Test
    @DisplayName("Debe impedir guardar PSU duplicada")
    void shouldThrowWhenPsuAlreadyExists() {

        when(repo.findByComponenteId(psuPrueba.getComponenteId()))
                .thenReturn(Optional.of(psuPrueba));

        assertThatThrownBy(() -> service.save(psuPrueba))
                .isInstanceOf(psuException.class)
                .hasMessage("El componente ya existe en la base de datos");

        verify(repo, times(1))
                .findByComponenteId(psuPrueba.getComponenteId());

        verify(repo, never())
                .save(any(powerSupply.class));
    }

    @Test
    @DisplayName("Debe actualizar una PSU")
    void shouldUpdatePsu() {

        Long id = psuPrueba.getId();

        powerSupply cambios = crearPsuFake();

        when(repo.findById(id))
                .thenReturn(Optional.of(psuPrueba));

        when(repo.save(any(powerSupply.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        powerSupply result = service.updateById(id, cambios);

        assertThat(result.getPotenciaWatts())
                .isEqualTo(cambios.getPotenciaWatts());

        assertThat(result.getCertificacion())
                .isEqualTo(cambios.getCertificacion());

        assertThat(result.getModular())
                .isEqualTo(cambios.getModular());

        assertThat(result.getConectoresPcie())
                .isEqualTo(cambios.getConectoresPcie());

        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(any(powerSupply.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar PSU inexistente")
    void shouldNotUpdatePsuWhenNotExists() {

        Long id = 999L;

        when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateById(id, crearPsuFake()))
                .isInstanceOf(psuException.class);

        verify(repo, times(1)).findById(id);
        verify(repo, never()).save(any(powerSupply.class));
    }

    @Test
    @DisplayName("Debe eliminar una PSU")
    void shouldDeletePsu() {

        Long id = psuPrueba.getId();

        service.deleteById(id);

        verify(repo, times(1)).deleteById(id);
    }
}
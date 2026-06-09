package msvc.msvc_ram.services;


import msvc.msvc_ram.Dto.RamDTO;
import msvc.msvc_ram.Model.Ram;
import msvc.msvc_ram.Repository.RamRepository;
import msvc.msvc_ram.Service.RamServiceImpl;
import net.datafaker.Faker;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/// palfindbycompponenteid
    private RamRepository ramRepository;

    @InjectMocks
    private RamServiceImpl ramService;

    private Ram ramPrueba;
    private List<Ram> ramList = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        this.ramPrueba = new Ram();
        this.ramPrueba.setMarca("d");

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 100; i++) {
            Ram ram = new Ram();
///estas lineas son para run formato //"XX-XX-XX-XX"
            String numeroSTR = faker.idNumber().valid().replace("-", "-1");
            String ultimo = numeroSTR.substring(numeroSTR.length() - 1);

        }


    }

@Test
@DisplayName("Shpuld be list all rams")
public void shouldBeListAllRams(){
    //Arrange
    List<Ram> rams = this.ramList;
    rams.add(this.ramPrueba);

    when(this.ramRepository.findAll()).thenReturn(rams);

    //ACT
    List<Ram>  list = this.ramService.listarTodos();

    //ASSERT
    assertThat(result).hasSize(101);
    }
@Test
@DisplayName("Debe buscar una ram con un id inexistente")
public void shouldNotFindRamById(){
Long id = 9999L;

        /// //.HASMESAGGE DEBE TENER EXACTAMENTE EL MISMO MENSAJE QUE serviceimpl

    //si tenemos 5 clients, deberiamos tener 5 mocks , en mock pongo todo con lo que me comunico
}


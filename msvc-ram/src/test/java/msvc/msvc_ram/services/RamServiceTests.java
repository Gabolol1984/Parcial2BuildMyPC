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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class
)
public class RamServiceTests {

    /// palfindbycompponenteid
    @Mock
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
ramList.add(ram);
        }


    }

    @Test
    @DisplayName("Should be list all rams")
    public void shouldBeListAllRams() {
        //Arrange
        List<Ram> rams = this.ramList;
        rams.add(this.ramPrueba);

        when(this.ramRepository.findAll()).thenReturn(rams);

        //ACT
        List<RamDTO> result = this.ramService.listarTodos();

        //ASSERT
        assertThat(result).hasSize(101);
        //
        assertThat(result.get(100).getMarca())
                .isEqualTo("d");

    }

    @Test
    @DisplayName("Debe buscar una ram con un id inexistente")
    public void shouldNotFindRamById() {
        Long id = 9999L;
        when(this.ramRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            this.ramService.buscarPorId(id);
            /// //.HASMESAGGE DEBE TENER EXACTAMENTE EL MISMO MENSAJE QUE serviceimpl
        }).isInstanceOf(RamException.class).hasMessage("Ram con id");



        /// //.HASMESAGGE DEBE TENER EXACTAMENTE EL MISMO MENSAJE QUE serviceimpl

        //si tenemos 5 clients, deberiamos tener 5 mocks , en mock pongo todo con lo que me comunico
    }
}

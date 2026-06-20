package com.buildmypc.msvc_quotation;

import com.buildmypc.msvc_quotation.service.CotizacionServiceImpl;

import com.buildmypc.msvc_quotation.dto.CotizacionResponseDTO;
import com.buildmypc.msvc_quotation.exception.ResourceNotFoundException;
import com.buildmypc.msvc_quotation.exception.ServiceException;
import com.buildmypc.msvc_quotation.feign.BuildClient;
import com.buildmypc.msvc_quotation.feign.ComponenteClient;
import com.buildmypc.msvc_quotation.model.Cotizacion;
import com.buildmypc.msvc_quotation.model.Cotizacion.EstadoCotizacion;
import com.buildmypc.msvc_quotation.repository.CotizacionRepository;
import com.buildmypc.msvc_quotation.repository.DetalleCotizacionRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuotationServiceTest {
    @Mock
    private CotizacionRepository cotizacionRepository;

    @Mock
    private DetalleCotizacionRepository detalleRepository;

    @Mock
    private BuildClient buildClient;

    @Mock
    private ComponenteClient componenteClient;

    @InjectMocks
    private CotizacionServiceImpl cotizacionService;

    private Cotizacion cotizacionPrueba;
    private List<Cotizacion> cotizacionList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.cotizacionPrueba = Cotizacion.builder()
                .id(1L)
                .buildId(10L)
                .usuarioId(5L)
                .subtotal(1000.0)
                .descuento(50.0)
                .total(950.0)
                .estado(EstadoCotizacion.PENDIENTE)
                .fechaEmision(LocalDateTime.now())
                .fechaVencimiento(LocalDateTime.now().plusDays(7))
                .build();

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 100; i++) {
            Cotizacion c = Cotizacion.builder()
                    .id((long) (i + 2))
                    .buildId((long) faker.number().numberBetween(11, 1000))
                    .usuarioId((long) faker.number().numberBetween(1, 500))
                    .subtotal(faker.number().randomDouble(2, 500, 3000))
                    .descuento(faker.number().randomDouble(2, 0, 100))
                    .estado(faker.options().option(EstadoCotizacion.values()))
                    .build();
            c.setTotal(c.getSubtotal() - c.getDescuento());
            this.cotizacionList.add(c);
        }
    }

    @Test
    @DisplayName("Debe listar todas las cotizaciones")
    public void shouldListAllQuotations() {
        List<Cotizacion> lista = this.cotizacionList;
        lista.add(this.cotizacionPrueba);

        when(this.cotizacionRepository.findAll()).thenReturn(lista);
        when(this.detalleRepository.findByCotizacionId(anyLong())).thenReturn(new ArrayList<>());

        List<CotizacionResponseDTO> result = this.cotizacionService.listarTodas();

        assertThat(result).hasSize(101);
        verify(cotizacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una cotización por su id")
    public void shouldFindQuotationById() {
        Long id = 1L;
        when(this.cotizacionRepository.findById(id)).thenReturn(Optional.of(this.cotizacionPrueba));
        when(this.detalleRepository.findByCotizacionId(id)).thenReturn(new ArrayList<>());

        CotizacionResponseDTO result = this.cotizacionService.buscarPorId(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTotal()).isEqualTo(950.0);
        verify(cotizacionRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepcion al buscar un id inexistente")
    public void shouldNotFindQuotationById() {
        Long id = 9999L;
        when(this.cotizacionRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            this.cotizacionService.buscarPorId(id);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cotización no encontrada con id: " + id);

        verify(cotizacionRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe listar cotizaciones por usuario id")
    public void shouldListQuotationsByUsuarioId() {
        Long usuarioId = 5L;
        when(this.cotizacionRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(this.cotizacionPrueba));
        when(this.detalleRepository.findByCotizacionId(anyLong())).thenReturn(new ArrayList<>());

        List<CotizacionResponseDTO> result = this.cotizacionService.listarPorUsuario(usuarioId);

        assertThat(result).hasSize(1);
        verify(cotizacionRepository, times(1)).findByUsuarioId(usuarioId);
    }

    @Test
    @DisplayName("Debe listar cotizaciones por estado")
    public void shouldListQuotationsByEstado() {
        String estado = "PENDIENTE";
        when(this.cotizacionRepository.findByEstado(EstadoCotizacion.PENDIENTE)).thenReturn(List.of(this.cotizacionPrueba));
        when(this.detalleRepository.findByCotizacionId(anyLong())).thenReturn(new ArrayList<>());

        List<CotizacionResponseDTO> result = this.cotizacionService.listarPorEstado(estado);

        assertThat(result).hasSize(1);
        verify(cotizacionRepository, times(1)).findByEstado(EstadoCotizacion.PENDIENTE);
    }

    @Test
    @DisplayName("Debe buscar una cotización por build id")
    public void shouldFindQuotationByBuildId() {
        Long buildId = 10L;
        when(this.cotizacionRepository.findByBuildId(buildId)).thenReturn(Optional.of(this.cotizacionPrueba));
        when(this.detalleRepository.findByCotizacionId(this.cotizacionPrueba.getId())).thenReturn(new ArrayList<>());

        CotizacionResponseDTO result = this.cotizacionService.buscarPorBuild(buildId);

        assertThat(result).isNotNull();
        assertThat(result.getBuildId()).isEqualTo(buildId);
        verify(cotizacionRepository, times(1)).findByBuildId(buildId);
    }

    @Test
    @DisplayName("Debe aprobar una cotización pendiente")
    public void shouldApproveQuotation() {
        Long id = 1L;
        when(this.cotizacionRepository.findById(id)).thenReturn(Optional.of(this.cotizacionPrueba));
        when(this.cotizacionRepository.save(any(Cotizacion.class))).thenAnswer(i -> i.getArgument(0));
        when(this.detalleRepository.findByCotizacionId(id)).thenReturn(new ArrayList<>());

        CotizacionResponseDTO result = this.cotizacionService.aprobar(id);

        assertThat(result.getEstado()).isEqualTo("APROBADA");
        verify(buildClient, times(1)).cambiarEstado(this.cotizacionPrueba.getBuildId(), "APROBADA");
        verify(cotizacionRepository, times(1)).save(any(Cotizacion.class));
    }

    @Test
    @DisplayName("Debe rechazar una cotización pendiente")
    public void shouldRejectQuotation() {
        Long id = 1L;
        when(this.cotizacionRepository.findById(id)).thenReturn(Optional.of(this.cotizacionPrueba));
        when(this.cotizacionRepository.save(any(Cotizacion.class))).thenAnswer(i -> i.getArgument(0));
        when(this.detalleRepository.findByCotizacionId(id)).thenReturn(new ArrayList<>());

        CotizacionResponseDTO result = this.cotizacionService.rechazar(id);

        assertThat(result.getEstado()).isEqualTo("RECHAZADA");
        verify(cotizacionRepository, times(1)).save(any(Cotizacion.class));
    }

    @Test
    @DisplayName("Debe lanzar excepcion al aprobar una cotización no pendiente")
    public void shouldNotApproveWhenNotPending() {
        Long id = 1L;
        this.cotizacionPrueba.setEstado(EstadoCotizacion.RECHAZADA);
        when(this.cotizacionRepository.findById(id)).thenReturn(Optional.of(this.cotizacionPrueba));

        assertThatThrownBy(() -> {
            this.cotizacionService.aprobar(id);
        }).isInstanceOf(ServiceException.class)
                .hasMessage("Solo se pueden aprobar cotizaciones PENDIENTES. Estado actual: RECHAZADA");

        verify(cotizacionRepository, never()).save(any(Cotizacion.class));
    }
}

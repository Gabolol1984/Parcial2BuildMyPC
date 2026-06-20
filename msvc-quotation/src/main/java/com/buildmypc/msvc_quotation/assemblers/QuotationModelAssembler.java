package com.buildmypc.msvc_quotation.assemblers;

import com.buildmypc.msvc_quotation.controller.CotizacionController;
import com.buildmypc.msvc_quotation.model.Cotizacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuotationModelAssembler implements RepresentationModelAssembler <Cotizacion, EntityModel<Cotizacion>> {
    @Override
    public EntityModel<Cotizacion> toModel(Cotizacion cotizacion) {
        // EntityModel.of(datos, ...enlaces) empaqueta el medico junto a sus links.
        return EntityModel.of(
                cotizacion,
                linkTo(methodOn(CotizacionController.class).buscarPorId(cotizacion.getId())).withSelfRel(),
                linkTo(methodOn(CotizacionController.class).listarTodas()).withRel("cotizaciones"));
    }
}

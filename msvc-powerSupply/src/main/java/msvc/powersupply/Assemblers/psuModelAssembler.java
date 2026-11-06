package msvc.powersupply.Assemblers;


import msvc.powersupply.Controller.psuControllerV2;
import msvc.powersupply.Model.powerSupply;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class psuModelAssembler implements RepresentationModelAssembler<powerSupply, EntityModel<powerSupply>> {

    @Override
    public EntityModel<powerSupply> toModel(powerSupply psu){
        return EntityModel.of(psu,
                linkTo(methodOn(psuControllerV2.class)
                        .findById(psu.getId())).withSelfRel(),

                linkTo(methodOn(psuControllerV2.class)
                        .getAllPsu()).withRel("power-supplies"),

                linkTo(methodOn(psuControllerV2.class)
                        .save(null)).withRel("crear"),

                linkTo(methodOn(psuControllerV2.class)
                        .updateById(psu.getId(), null)).withRel("actualizar"),

                linkTo(methodOn(psuControllerV2.class)
                        .deleteById(psu.getId())).withRel("eliminar")
        );
    }
}
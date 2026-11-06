package msvc.motherboard.assemblers;

import msvc.motherboard.Controller.MotherboardController;
import msvc.motherboard.Model.Motherboard;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MotherboardModelAssembler implements RepresentationModelAssembler<Motherboard, EntityModel<Motherboard>> {

    @Override
    public EntityModel<Motherboard> toModel(Motherboard motherboard) {
        return EntityModel.of(
                motherboard,
                linkTo(methodOn(MotherboardController.class).findById(motherboard.getId())).withSelfRel(),
                linkTo(methodOn(MotherboardController.class).findAll()).withRel("motherboards"));
    }
}

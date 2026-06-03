package msvc.msvc_ram.assemblers;

import msvc.msvc_ram.Controller.RamController;
import msvc.msvc_ram.Model.Ram;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RamModelAssembler implements RepresentationModelAssembler<Ram, EntityModel<Ram>> {

    @Override
    public EntityModel<Ram> toModel(Ram ram){
        return EntityModel.of((
                ram,
                linkTo(methodOn(RamControllerV2))

                ))
    }

}

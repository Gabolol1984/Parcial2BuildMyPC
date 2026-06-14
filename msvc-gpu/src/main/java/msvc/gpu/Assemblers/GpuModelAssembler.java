package msvc.gpu.Assemblers;


import msvc.gpu.Controller.GpuControllerV2;
import msvc.gpu.Model.Gpu;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GpuModelAssembler
        implements RepresentationModelAssembler<Gpu, EntityModel<Gpu>> {

    @Override
    public EntityModel<Gpu> toModel(Gpu gpu) {

        return EntityModel.of(gpu,
                linkTo(methodOn(GpuControllerV2.class)
                        .findById(gpu.getComponenteId())).withSelfRel(),

                linkTo(methodOn(GpuControllerV2.class)
                        .findAll()).withRel("gpus"),

                //////pendiente (?)
                linkTo(methodOn(GpuControllerV2.class)
                        .create(null)).withRel("crear"),

                linkTo(methodOn(GpuControllerV2.class)
                        .update(gpu.getComponenteId(), null)).withRel("actualizar"),

                linkTo(methodOn(GpuControllerV2.class)
                        .delete(gpu.getComponenteId())).withRel("eliminar")
        );
    }
}
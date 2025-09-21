package com.tienda.multimedia.adaptador.puerto.entrada;

import com.tienda.multimedia.adaptador.modelo.controller.ImagenProductoControllerModel;
import com.tienda.multimedia.domino.ImagenProductoDomainModel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperControllerToDomainImagen {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nombreArchivo", target = "nombreArchivo"),
            @Mapping(source = "principal", target = "principal"),
            @Mapping(source = "orden", target = "orden")
    })
    ImagenProductoControllerModel toControllerModel(ImagenProductoDomainModel domainModel);
    @InheritInverseConfiguration
    @Mapping(target = "idPublicacion",ignore = true)
    ImagenProductoDomainModel toDomainModel(ImagenProductoControllerModel controllerModel);
}

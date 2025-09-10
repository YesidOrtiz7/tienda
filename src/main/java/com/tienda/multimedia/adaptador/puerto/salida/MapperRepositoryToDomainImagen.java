package com.tienda.multimedia.adaptador.puerto.salida;

import com.tienda.multimedia.adaptador.modelo.persistencia.ImagenProductoPersistenceModel;
import com.tienda.multimedia.domino.ImagenProductoDomainModel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperRepositoryToDomainImagen {
    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "nombreArchivo",target = "nombreArchivo"),
            @Mapping(source = "publicacion.idPublicacion", target = "idPublicacion")
    })
    ImagenProductoDomainModel toDomainModel(ImagenProductoPersistenceModel persistenceModel);
    @InheritInverseConfiguration
    @Mapping(target = "publicacion",ignore=true)//evitando bucle inverso
    ImagenProductoPersistenceModel toPersistenceModel(ImagenProductoDomainModel domainModel);
}

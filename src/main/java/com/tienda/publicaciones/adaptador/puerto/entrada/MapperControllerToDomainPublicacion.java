package com.tienda.publicaciones.adaptador.puerto.entrada;

import com.tienda.publicaciones.adaptador.modelo.PublicacionControllerModel;
import com.tienda.publicaciones.dominio.Publicacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperControllerToDomainPublicacion {
    @Mappings({
            @Mapping(source = "tituloPublicacion",target = "tituloPublicacion"),
            @Mapping(source = "descripcion",target = "descripcion"),
            @Mapping(source = "precio",target = "precio"),
            @Mapping(source = "categoria",target = "categoria"),
            @Mapping(source = "cantidadDisponible",target = "cantidadDisponible"),
            @Mapping(source = "fechaPublicacion",target = "fechaPublicacion")
    })
    Publicacion toDomainModel(PublicacionControllerModel controllerModel);

}

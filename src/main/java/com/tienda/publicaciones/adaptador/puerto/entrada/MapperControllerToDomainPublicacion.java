package com.tienda.publicaciones.adaptador.puerto.entrada;

import com.tienda.multimedia.adaptador.puerto.entrada.MapperControllerToDomainImagen;
import com.tienda.publicaciones.adaptador.modelo.PublicacionControllerModel;
import com.tienda.publicaciones.dominio.Publicacion;
import com.tienda.usuarios.adaptador.puerto.entrada.MapperDomainUsuarioToUsuarioBasicData;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses={MapperControllerToDomainImagen.class, MapperDomainUsuarioToUsuarioBasicData.class})
public interface MapperControllerToDomainPublicacion {
    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "tituloPublicacion",target = "tituloPublicacion"),
            @Mapping(source = "descripcion",target = "descripcion"),
            @Mapping(source = "precio",target = "precio"),
            @Mapping(source = "categoria",target = "categoria"),
            @Mapping(source = "cantidadDisponible",target = "cantidadDisponible"),
            @Mapping(source = "fechaPublicacion",target = "fechaPublicacion"),
            @Mapping(source = "usuario",target = "usuario"),
            @Mapping(source = "visible",target = "visible"),
            @Mapping(source = "imagenes",target = "imagenes")
    })
    Publicacion toDomainModel(PublicacionControllerModel controllerModel);

    @InheritInverseConfiguration
    PublicacionControllerModel toControllerModel(Publicacion domainModel);
}

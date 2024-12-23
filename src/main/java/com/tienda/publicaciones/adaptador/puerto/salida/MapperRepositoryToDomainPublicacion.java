package com.tienda.publicaciones.adaptador.puerto.salida;

import com.tienda.categoriasProductos.adaptador.puerto.salida.MapperRepositoryToDomainCategoria;
import com.tienda.publicaciones.adaptador.modelo.PublicacionPersistenceModel;
import com.tienda.publicaciones.dominio.Publicacion;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.MapperRepositoryToDomainUsuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MapperRepositoryToDomainCategoria.class, MapperRepositoryToDomainUsuario.class})
public interface MapperRepositoryToDomainPublicacion {
    @Mappings({
            @Mapping(source = "idPublicacion",target = "id"),
            @Mapping(source = "tituloPublicacion",target = "tituloPublicacion"),
            @Mapping(source = "descripcion",target = "descripcion"),
            @Mapping(source = "precio",target = "precio"),
            @Mapping(source = "categoriaId",target = "categoria"),
            @Mapping(source = "cantidadDisponible",target = "cantidadDisponible"),
            @Mapping(source = "fechaPublicacion",target = "fechaPublicacion"),
            @Mapping(source = "usuario",target = "usuario")
    })
    Publicacion toDomainModel(PublicacionPersistenceModel persistenceModel);
    @Mappings({
            @Mapping(source = "id",target = "idPublicacion"),
            @Mapping(source = "tituloPublicacion",target = "tituloPublicacion"),
            @Mapping(source = "descripcion",target = "descripcion"),
            @Mapping(source = "precio",target = "precio"),
            @Mapping(source = "categoria",target = "categoriaId"),
            @Mapping(source = "cantidadDisponible",target = "cantidadDisponible"),
            @Mapping(source = "fechaPublicacion",target = "fechaPublicacion"),
            @Mapping(source = "usuario",target = "usuario")
    })
    PublicacionPersistenceModel toPersistenceModel(Publicacion domainModel);
}

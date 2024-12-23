package com.tienda.categoriasProductos.adaptador.puerto.salida;

import com.tienda.categoriasProductos.adaptador.modelo.CategoriaPersistenceModel;
import com.tienda.categoriasProductos.dominio.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperRepositoryToDomainCategoria {
    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "nombre",target = "nombre")
    })
    Categoria toModel(CategoriaPersistenceModel persistenceModel);

    @InheritInverseConfiguration
    CategoriaPersistenceModel toPersistenceModel(Categoria model);
}

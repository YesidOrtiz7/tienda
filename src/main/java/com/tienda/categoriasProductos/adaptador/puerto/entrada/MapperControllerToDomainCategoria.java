package com.tienda.categoriasProductos.adaptador.puerto.entrada;

import com.tienda.categoriasProductos.adaptador.modelo.CategoriaControllerModel;
import com.tienda.categoriasProductos.dominio.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperControllerToDomainCategoria {
    @Mappings({
            @Mapping(source = "nombre", target = "nombre")
    })
    Categoria toDomainModel(CategoriaControllerModel controllerModel);
    @InheritInverseConfiguration
    CategoriaControllerModel toControllerModel(Categoria domainModel);
}

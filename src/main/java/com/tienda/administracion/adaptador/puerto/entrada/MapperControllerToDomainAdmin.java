package com.tienda.administracion.adaptador.puerto.entrada;

import com.tienda.administracion.adaptador.modelo.AdministradorControllerModel;
import com.tienda.administracion.dominio.Administrador;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperControllerToDomainAdmin {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "documento", target = "documento"),
            @Mapping(source = "nombres", target = "nombres"),
            @Mapping(source = "apellidos", target = "apellidos"),
            @Mapping(source = "habilitado", target = "habilitado")
    })
    Administrador toDomainModel(AdministradorControllerModel controllerModel);

    @InheritInverseConfiguration
    AdministradorControllerModel toControllerModel(Administrador domainModel);
}

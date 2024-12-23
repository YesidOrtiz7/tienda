package com.tienda.usuarios.adaptador.puerto.entrada;

import com.tienda.usuarios.adaptador.modelo.CrearUsuario_ControllerModel;
import com.tienda.usuarios.dominio.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperControllerToDomainUsuario_CrearUsuario {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "documento", target = "documento"),
            @Mapping(source = "primerNombre", target = "primerNombre"),
            @Mapping(source = "segundoNombre", target = "segundoNombre"),
            @Mapping(source = "primerApellido", target = "primerApellido"),
            @Mapping(source = "segundoApellido", target = "segundoApellido"),
            @Mapping(source = "contrasena", target = "contrasena")
    })
    CrearUsuario_ControllerModel toControllerModel(Usuario domainModel);

    @InheritInverseConfiguration
    Usuario toDomainModel(CrearUsuario_ControllerModel controllerModel);
}

package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.usuarios.adaptador.modelo.UsuarioPersistenceModel;
import com.tienda.usuarios.dominio.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperRepositoryToDomainUsuario {
    @Mappings({
        @Mapping(source = "id",target = "id"),
        @Mapping(source = "documento",target = "documento"),
        @Mapping(source = "primerNombre",target = "primerNombre"),
        @Mapping(source = "segundoNombre",target = "segundoNombre"),
        @Mapping(source = "primerApellido",target = "primerApellido"),
        @Mapping(source = "segundoApellido",target = "segundoApellido"),
        @Mapping(source = "contrasena",target = "contrasena"),
        @Mapping(source = "totpSecret",target = "totpSecret"),
        @Mapping(source = "habilitado",target = "habilitado"),
        @Mapping(source = "saldoEnCuenta",target = "saldoEnCuenta"),
    })
    UsuarioPersistenceModel toPersistenceModel(Usuario usuario);
    @InheritInverseConfiguration
    Usuario toDomainModel(UsuarioPersistenceModel usuario);
}

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
        @Mapping(source = "correo",target = "correo"),
        @Mapping(source = "nombres",target = "nombres"),
        @Mapping(source = "apellidos",target = "apellidos"),
        @Mapping(source = "telefono",target = "telefono"),
        @Mapping(source = "contrasena",target = "contrasena"),
        @Mapping(source = "totpSecret",target = "totpSecret"),
        @Mapping(source = "bloqueado",target = "bloqueado"),
        @Mapping(source = "eliminado",target = "eliminado"),
        @Mapping(source = "saldoEnCuenta",target = "saldoEnCuenta"),
    })
    UsuarioPersistenceModel toPersistenceModel(Usuario usuario);
    @InheritInverseConfiguration
    Usuario toDomainModel(UsuarioPersistenceModel usuario);
}

package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.cuentas.adaptador.puerto.salida.MapperRepositoryToDomainCuenta;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol.MapperRepositoryToDomainRol;
import com.tienda.usuarios.dominio.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MapperRepositoryToDomainRol.class, MapperRepositoryToDomainCuenta.class})
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
        @Mapping(target = "cuenta",ignore = true),
        @Mapping(target = "compras",ignore = true)
    })
    UsuarioPersistenceModel toPersistenceModel(Usuario usuario);
    @InheritInverseConfiguration
    @Mapping(target = "roles",ignore = true)
    Usuario toDomainModel(UsuarioPersistenceModel usuario);
}

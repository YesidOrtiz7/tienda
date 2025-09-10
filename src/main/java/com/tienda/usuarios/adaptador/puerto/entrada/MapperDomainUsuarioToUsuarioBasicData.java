package com.tienda.usuarios.adaptador.puerto.entrada;

import com.tienda.usuarios.adaptador.modelo.controller.UsuarioBasicData;
import com.tienda.usuarios.adaptador.modelo.controller.UsuarioBasicDataExtended;
import com.tienda.usuarios.dominio.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperDomainUsuarioToUsuarioBasicData {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "documento", target = "documento"),
            @Mapping(source = "correo", target = "correo"),
            @Mapping(source = "nombres", target = "nombres"),
            @Mapping(source = "apellidos", target = "apellidos"),
            @Mapping(source = "telefono", target = "telefono")
    })
    UsuarioBasicData toBasicDataModel(Usuario usuario);

    @InheritInverseConfiguration
    @Mapping(target = "contrasena",ignore = true)
    @Mapping(target = "totpSecret",ignore = true)
    @Mapping(target = "bloqueado",ignore = true)
    @Mapping(target = "eliminado",ignore = true)
    @Mapping(target = "roles",ignore = true)
    Usuario toBasicDomainModel(UsuarioBasicData usuarioBasicData);
}

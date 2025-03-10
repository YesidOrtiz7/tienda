package com.tienda.usuarios.adaptador.puerto.entrada;

import com.tienda.usuarios.adaptador.modelo.controller.UsuarioBasicData;
import com.tienda.usuarios.adaptador.modelo.controller.UsuarioBasicDataExtended;
import com.tienda.usuarios.dominio.Usuario;
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

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "documento", target = "documento"),
            @Mapping(source = "correo", target = "correo"),
            @Mapping(source = "nombres", target = "nombres"),
            @Mapping(source = "apellidos", target = "apellidos"),
            @Mapping(source = "telefono", target = "telefono"),
            @Mapping(source = "bloqueado", target = "bloqueado"),
            @Mapping(source = "eliminado", target = "eliminado"),
            @Mapping(source = "roles", target = "roles"),
            @Mapping(source = "cuenta", target = "cuenta")
    })
    UsuarioBasicDataExtended toBasicDataExtendedModel(Usuario usuario);
}

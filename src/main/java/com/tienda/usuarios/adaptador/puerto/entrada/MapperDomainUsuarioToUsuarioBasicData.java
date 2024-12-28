package com.tienda.usuarios.adaptador.puerto.entrada;

import com.tienda.usuarios.adaptador.modelo.controller.UsuarioBasicData;
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
}

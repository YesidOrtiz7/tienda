package com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol;

import com.tienda.usuarios.adaptador.modelo.persistencia.RolEntity;
import com.tienda.usuarios.dominio.Rol;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperRepositoryToDomainRol {
    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "nombre",target = "nombre")
    })
    RolEntity toPersistenceModel(Rol rol);

    @InheritInverseConfiguration
    Rol toDomainModel(RolEntity rol);
}

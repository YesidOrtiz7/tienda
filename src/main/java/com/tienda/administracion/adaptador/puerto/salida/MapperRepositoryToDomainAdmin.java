package com.tienda.administracion.adaptador.puerto.salida;

import com.tienda.administracion.adaptador.modelo.AdministradorPersistenceModel;
import com.tienda.administracion.dominio.Administrador;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperRepositoryToDomainAdmin {
    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "documento",target = "documento"),
            @Mapping(source = "nombres",target = "nombres"),
            @Mapping(source = "apellidos",target = "apellidos"),
            @Mapping(source = "contrasena",target = "contrasena"),
            @Mapping(source = "totpSecret",target = "totpSecret"),
            @Mapping(source = "habilitado",target = "habilitado")
    })
    Administrador toDomainModel(AdministradorPersistenceModel persistenceModel);
    @InheritInverseConfiguration
    AdministradorPersistenceModel toPersistenceModel(Administrador domainModel);
}

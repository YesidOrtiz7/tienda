package com.tienda.compras.adaptador.puerto.salida.persistencia.orden;

import com.tienda.compras.adaptador.modelo.persistencia.OrdenEntity;
import com.tienda.compras.dominio.Orden;
import com.tienda.publicaciones.adaptador.puerto.salida.MapperRepositoryToDomainPublicacion;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses={ MapperRepositoryToDomainPublicacion.class})
public interface MapperRepositoryToDomainOrden {
    @Mappings({
            @Mapping(source = "id_orden",target = "id_orden"),
            @Mapping(source = "producto",target = "publicacion"),
            @Mapping(source = "cantidad",target = "cantidad")
    })
    OrdenEntity toPersistenceModel(Orden domainModel);
    @InheritInverseConfiguration
    Orden toDomainModel(OrdenEntity persistenceModel);
}

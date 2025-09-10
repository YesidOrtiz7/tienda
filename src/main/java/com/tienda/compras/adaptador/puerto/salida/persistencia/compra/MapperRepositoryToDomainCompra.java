package com.tienda.compras.adaptador.puerto.salida.persistencia.compra;

import com.tienda.compras.adaptador.modelo.persistencia.CompraEntity;
import com.tienda.compras.adaptador.puerto.salida.persistencia.orden.MapperRepositoryToDomainOrden;
import com.tienda.compras.dominio.Compra;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.MapperRepositoryToDomainUsuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MapperRepositoryToDomainOrden.class, MapperRepositoryToDomainUsuario.class})
public interface MapperRepositoryToDomainCompra {
    @Mappings({
            @Mapping(source = "id_compra",target = "id_compra"),
            @Mapping(source = "usuario",target = "usuario"),
            @Mapping(source = "ordenes",target = "orden_id"),
            @Mapping(source = "total",target = "total"),
            @Mapping(source = "fecha",target = "fecha")
    })
    CompraEntity toPersistenceModel(Compra domainModel);

    @InheritInverseConfiguration
    Compra toDomainModel(CompraEntity persistenceModel);
}

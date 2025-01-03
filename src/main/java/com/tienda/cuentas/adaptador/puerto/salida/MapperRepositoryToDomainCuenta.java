package com.tienda.cuentas.adaptador.puerto.salida;

import com.tienda.cuentas.adaptador.puerto.salida.modelos.CuentaEntity;
import com.tienda.cuentas.dominio.Cuenta;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapperRepositoryToDomainCuenta {
    @Mappings({
            @Mapping(source = "id_usuario",target = "id_usuario"),
            @Mapping(source = "saldo",target = "saldo")
    })
    CuentaEntity toPersistenceModel(Cuenta model);

    @InheritInverseConfiguration
    Cuenta toDomainModel(CuentaEntity model);
}

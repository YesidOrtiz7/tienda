package com.tienda.cuentas.adaptador.puerto.salida;

import com.tienda.cuentas.adaptador.puerto.salida.modelos.CuentaEntity;
import org.springframework.data.repository.CrudRepository;

public interface CuentaCrudRepository extends CrudRepository<CuentaEntity,Integer> {
}

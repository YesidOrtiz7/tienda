package com.tienda.compras.adaptador.puerto.salida.persistencia.orden;

import com.tienda.compras.adaptador.modelo.persistencia.OrdenEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrdenCrudRepository extends CrudRepository<OrdenEntity,Integer> {
}

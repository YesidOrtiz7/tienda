package com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol;

import com.tienda.usuarios.adaptador.modelo.persistencia.RolEntity;
import org.springframework.data.repository.CrudRepository;

public interface RolCrudRepository extends CrudRepository<RolEntity,Integer> {
}

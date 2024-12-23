package com.tienda.administracion.adaptador.puerto.salida;

import com.tienda.administracion.adaptador.modelo.AdministradorPersistenceModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminCrudRepository extends CrudRepository<AdministradorPersistenceModel,Integer> {
    Optional<AdministradorPersistenceModel> findByDocumento(String documento);
}

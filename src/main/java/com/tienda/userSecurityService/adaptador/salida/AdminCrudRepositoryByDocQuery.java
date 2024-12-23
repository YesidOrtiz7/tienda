package com.tienda.userSecurityService.adaptador.salida;

import com.tienda.administracion.adaptador.modelo.AdministradorPersistenceModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminCrudRepositoryByDocQuery extends CrudRepository<AdministradorPersistenceModel,Integer> {
    Optional<AdministradorPersistenceModel> findByDocumento(String documento);
}

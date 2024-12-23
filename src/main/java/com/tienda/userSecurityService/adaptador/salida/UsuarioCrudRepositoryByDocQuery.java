package com.tienda.userSecurityService.adaptador.salida;

import com.tienda.usuarios.adaptador.modelo.UsuarioPersistenceModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioCrudRepositoryByDocQuery extends CrudRepository<UsuarioPersistenceModel,Integer> {
    Optional<UsuarioPersistenceModel> findByDocumento(String documento);
}

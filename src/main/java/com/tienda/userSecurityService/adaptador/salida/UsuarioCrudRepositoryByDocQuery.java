package com.tienda.userSecurityService.adaptador.salida;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioCrudRepositoryByDocQuery extends CrudRepository<UsuarioPersistenceModel,Integer> {
    @EntityGraph(attributePaths = {"usuarioRoles", "usuarioRoles.rol"})
    Optional<UsuarioPersistenceModel> findByDocumento(String documento);
}

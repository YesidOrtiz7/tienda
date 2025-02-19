package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioCrudRepository extends CrudRepository<UsuarioPersistenceModel,Integer> {
    Optional<UsuarioPersistenceModel> findByDocumento(String documento);
    boolean existsByDocumento(String document);
}

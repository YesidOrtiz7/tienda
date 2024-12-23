package com.tienda.publicaciones.adaptador.puerto.salida;

import com.tienda.publicaciones.adaptador.modelo.PublicacionPersistenceModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PublicacionesCrudRepository extends CrudRepository<PublicacionPersistenceModel,Integer> {
    @Override
    Optional<PublicacionPersistenceModel> findById(Integer integer);

    @Override
    Iterable<PublicacionPersistenceModel> findAll();

    @Override
    void deleteById(Integer integer);

    @Override
    boolean existsById(Integer integer);
}

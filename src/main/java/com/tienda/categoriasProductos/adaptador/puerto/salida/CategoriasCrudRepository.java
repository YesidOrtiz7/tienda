package com.tienda.categoriasProductos.adaptador.puerto.salida;

import com.tienda.categoriasProductos.adaptador.modelo.CategoriaPersistenceModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoriasCrudRepository extends CrudRepository<CategoriaPersistenceModel,Integer> {

    @Override
    Optional<CategoriaPersistenceModel> findById(Integer integer);

    @Override
    boolean existsById(Integer integer);

    @Override
    Iterable<CategoriaPersistenceModel> findAll();

    @Override
    void deleteById(Integer integer);
}

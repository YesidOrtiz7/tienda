package com.tienda.multimedia.adaptador.puerto.salida;

import com.tienda.multimedia.adaptador.modelo.persistencia.ImagenProductoPersistenceModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ImagenCrudRepository extends CrudRepository<ImagenProductoPersistenceModel, Integer> {
    List<ImagenProductoPersistenceModel> findByPublicacion_IdPublicacion(Long publicacionId);
    Optional<ImagenProductoPersistenceModel> findByNombreArchivo(String nombre);
    boolean existsByNombreArchivo(String nombre);
}

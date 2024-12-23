package com.tienda.publicaciones.adaptador.puerto.salida;

import com.tienda.categoriasProductos.aplicacion.puerto.salida.CategoriaPortOut;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.publicaciones.adaptador.modelo.PublicacionPersistenceModel;
import com.tienda.publicaciones.aplicacion.puerto.salida.PublicacionPortOut;
import com.tienda.publicaciones.dominio.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class PublicacionesRepository implements PublicacionPortOut {
    private PublicacionesCrudRepository repository;
    private CategoriaPortOut repositoryCategoria;
    private MapperRepositoryToDomainPublicacion mapper;
    /*-----------------------------------------------------------------*/
    @Autowired
    public void setRepository(PublicacionesCrudRepository repository) {
        this.repository = repository;
    }
    @Autowired
    public void setMapper(MapperRepositoryToDomainPublicacion mapper) {
        this.mapper = mapper;
    }
    @Autowired
    public void setRepositoryCategoria(CategoriaPortOut repositoryCategoria) {
        this.repositoryCategoria = repositoryCategoria;
    }
    /*-----------------------------------------------------------------*/

    @Override
    public ArrayList<Publicacion> obtenerPublicaciones() {
        ArrayList<Publicacion> response=new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(
                (i)->{response.add(mapper.toDomainModel(i));}
        );
        return response;
    }

    @Override
    public Publicacion obtenerPublicacion(int id) throws SearchItemNotFoundException {
        Optional<PublicacionPersistenceModel> response=repository.findById(id);
        if (response.isEmpty()){
            throw new SearchItemNotFoundException("No se encontro la publicacion");
        }
        return mapper.toDomainModel(response.get());
    }

    @Override
    public Publicacion crearPublicacion(Publicacion publicacion) throws ItemAlreadyExistException, SearchItemNotFoundException {
        if (!repositoryCategoria.existePorId(publicacion.getCategoria().getId())){
            throw new SearchItemNotFoundException("La categoria no existe");
        }
        if (repository.existsById(publicacion.getId())){
            throw new ItemAlreadyExistException("La publicacion ya existe");
        }
        PublicacionPersistenceModel response=repository.save(mapper.toPersistenceModel(publicacion));

        return mapper.toDomainModel(response);
    }

    @Override
    public Publicacion actualizarPublicacion(Publicacion publicacion) throws SearchItemNotFoundException {
        if (!repositoryCategoria.existePorId(publicacion.getCategoria().getId())){
            throw new SearchItemNotFoundException("La categoria no existe");
        }
        if (repository.existsById(publicacion.getId())){
            PublicacionPersistenceModel response=repository.save(mapper.toPersistenceModel(publicacion));

            return mapper.toDomainModel(response);
        }

        throw new SearchItemNotFoundException("La publicacion ya existe");
    }

    @Override
    public boolean eliminarPublicacion(int id) throws SearchItemNotFoundException {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        throw new SearchItemNotFoundException("La publicacion no existe");
    }
}

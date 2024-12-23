package com.tienda.categoriasProductos.adaptador.puerto.salida;

import com.tienda.categoriasProductos.adaptador.modelo.CategoriaPersistenceModel;
import com.tienda.categoriasProductos.aplicacion.puerto.salida.CategoriaPortOut;
import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CategoriasRepository implements CategoriaPortOut {
    private CategoriasCrudRepository repository;
    private MapperRepositoryToDomainCategoria mapper;

    /*----------------------------------------------------------------------*/
    @Autowired
    public void setRepository(CategoriasCrudRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(MapperRepositoryToDomainCategoria mapper) {
        this.mapper = mapper;
    }

    /*----------------------------------------------------------------------*/

    @Override
    public boolean crearCategoria(Categoria categoria) throws ItemAlreadyExistException {
        if (repository.existsById(categoria.getId())){
            throw new ItemAlreadyExistException("El id de la categoria ya existe en la base de datos");
        }
        repository.save(mapper.toPersistenceModel(categoria));
        return true;
    }

    @Override
    public Categoria actualizarCategoria(Categoria categoria) throws SearchItemNotFoundException {
        if (repository.existsById(categoria.getId())){
            return mapper.toModel(
                    repository.save(mapper.toPersistenceModel(categoria))
            );
        }
        throw new SearchItemNotFoundException("La categoria a actualizar no se encuentra registrada en la base "+
                "de datos");
    }

    @Override
    public boolean eliminarCategoria(int id) throws SearchItemNotFoundException {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        throw new SearchItemNotFoundException("La categoria no existe");

    }

    @Override
    public ArrayList<Categoria> consultarCategorias() {
        ArrayList<Categoria> response=new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(
                (i)->{response.add(mapper.toModel(i));}
        );
        return response;
    }

    @Override
    public Categoria obtenerCategoriaPorId(int id) throws SearchItemNotFoundException {
        CategoriaPersistenceModel response =repository.findById(id).orElseThrow(()->new SearchItemNotFoundException("No existe la categoria"));
        return mapper.toModel(response);
    }

    @Override
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }
}

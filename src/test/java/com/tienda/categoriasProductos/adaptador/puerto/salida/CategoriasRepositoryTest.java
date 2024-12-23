package com.tienda.categoriasProductos.adaptador.puerto.salida;

import com.tienda.categoriasProductos.adaptador.modelo.CategoriaPersistenceModel;
import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

//import static org.junit.jupiter.api.Assertions.*;

class CategoriasRepositoryTest {
    private CategoriasCrudRepository persistence;
    private CategoriasRepository repository=new CategoriasRepository();
    private MapperRepositoryToDomainCategoria mapper;
    private CategoriaPersistenceModel categoria;
    private Categoria categoriaDomainModel;
    private ArrayList<CategoriaPersistenceModel> categorias=new ArrayList<>();
    @BeforeEach
    void setUp(){
        persistence = Mockito.mock(CategoriasCrudRepository.class);
        mapper=Mockito.mock(MapperRepositoryToDomainCategoria.class);
        repository.setMapper(mapper);
        repository.setRepository(persistence);
        categoria=new CategoriaPersistenceModel();
        categoria.setId(1);
        categoria.setNombre("tomate");
        categoriaDomainModel=new Categoria(1,"tomate");
        categorias.add(categoria);
    }

    @Test
    void crearCategoria() {
        try {
            Mockito.when(persistence.save(categoria)).thenReturn(categoria);
            Mockito.when(persistence.existsById(categoria.getId())).thenReturn(false);
            Mockito.when(mapper.toPersistenceModel(categoriaDomainModel)).thenReturn(categoria);
            Assertions.assertTrue(repository.crearCategoria(categoriaDomainModel));
            Mockito.verify(persistence,Mockito.times(1)).save(categoria);
            Mockito.verify(persistence,Mockito.times(1)).existsById(categoria.getId());
            Mockito.verify(mapper,Mockito.times(1)).toPersistenceModel(categoriaDomainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearCategoria_CategoriaYaExiste() {
        try {
            Mockito.when(persistence.save(categoria)).thenReturn(categoria);
            Mockito.when(persistence.existsById(categoria.getId())).thenReturn(true);
            Mockito.when(mapper.toPersistenceModel(categoriaDomainModel)).thenReturn(categoria);
            Assertions.assertThrows(ItemAlreadyExistException.class,()->repository.crearCategoria(categoriaDomainModel));
            Mockito.verify(persistence,Mockito.times(0)).save(categoria);
            Mockito.verify(persistence,Mockito.times(1)).existsById(categoria.getId());
            Mockito.verify(mapper,Mockito.times(0)).toPersistenceModel(categoriaDomainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void actualizarCategoria() {
        try {
            Mockito.when(persistence.save(categoria)).thenReturn(categoria);
            Mockito.when(persistence.existsById(categoria.getId())).thenReturn(true);
            Mockito.when(mapper.toPersistenceModel(categoriaDomainModel)).thenReturn(categoria);
            Mockito.when(mapper.toModel(categoria)).thenReturn(categoriaDomainModel);
            Assertions.assertEquals(categoriaDomainModel,repository.actualizarCategoria(categoriaDomainModel));
            Mockito.verify(persistence,Mockito.times(1)).save(categoria);
            Mockito.verify(persistence,Mockito.times(1)).existsById(categoria.getId());
            Mockito.verify(mapper,Mockito.times(1)).toPersistenceModel(categoriaDomainModel);
            Mockito.verify(mapper,Mockito.times(1)).toModel(categoria);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarCategoria_YaExiste() {
        try {
            Mockito.when(persistence.save(categoria)).thenReturn(categoria);
            Mockito.when(persistence.existsById(categoria.getId())).thenReturn(false);
            Mockito.when(mapper.toPersistenceModel(categoriaDomainModel)).thenReturn(categoria);
            Mockito.when(mapper.toModel(categoria)).thenReturn(categoriaDomainModel);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->repository.actualizarCategoria(categoriaDomainModel));
            Mockito.verify(persistence,Mockito.times(0)).save(categoria);
            Mockito.verify(persistence,Mockito.times(1)).existsById(categoria.getId());
            Mockito.verify(mapper,Mockito.times(0)).toPersistenceModel(categoriaDomainModel);
            Mockito.verify(mapper,Mockito.times(0)).toModel(categoria);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void eliminarCategoria() {
        try {
            Mockito.when(persistence.existsById(categoria.getId())).thenReturn(true);
            Assertions.assertTrue(repository.eliminarCategoria(categoria.getId()));
            Mockito.verify(persistence,Mockito.times(1)).deleteById(categoria.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void eliminarCategoria_NoExisteCategoria() {
        try {
            Mockito.when(persistence.existsById(categoria.getId())).thenReturn(false);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->repository.eliminarCategoria(categoria.getId()));
            Mockito.verify(persistence,Mockito.times(0)).deleteById(categoria.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void consultarCategorias() {
        Mockito.when(persistence.findAll()).thenReturn(categorias);
        repository.consultarCategorias();
        Mockito.verify(persistence,Mockito.times(1)).findAll();
    }
    @Test
    void obtenerCategoriaPorId(){
        try {
            Mockito.when(persistence.findById(categoria.getId())).thenReturn(Optional.ofNullable(categoria));
            Mockito.when(mapper.toModel(categoria)).thenReturn(categoriaDomainModel);
            Assertions.assertEquals(categoriaDomainModel,repository.obtenerCategoriaPorId(categoria.getId()));
            Mockito.verify(persistence,Mockito.times(1)).findById(categoria.getId());
            Mockito.verify(mapper,Mockito.times(1)).toModel(categoria);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void obtenerCategoriaPorId_CategoriaNoExiste(){
        try {
            Mockito.when(persistence.findById(categoria.getId())).thenReturn(Optional.empty());
            Mockito.when(mapper.toModel(categoria)).thenReturn(categoriaDomainModel);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->repository.obtenerCategoriaPorId(categoria.getId()));
            Mockito.verify(persistence,Mockito.times(1)).findById(categoria.getId());
            Mockito.verify(mapper,Mockito.times(0)).toModel(categoria);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void existePorId(){
        Mockito.when(persistence.existsById(categoria.getId())).thenReturn(true);
        Assertions.assertTrue(repository.existePorId(categoria.getId()));
    }
    @Test
    void existePorId_CategoriaNoExiste(){
        Mockito.when(persistence.existsById(categoria.getId())).thenReturn(false);
        Assertions.assertFalse(repository.existePorId(categoria.getId()));
    }
}
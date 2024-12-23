package com.tienda.categoriasProductos.aplicacion;

import com.tienda.categoriasProductos.aplicacion.puerto.salida.CategoriaPortOut;
import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

//import static org.junit.jupiter.api.Assertions.*;
class CategoriaServiceTest {
    private CategoriaPortOut repository;
    private CategoriaService service=new CategoriaService();
    private Categoria categoria1;
    private ArrayList<Categoria> categorias=new ArrayList<>();
    @BeforeEach
    void setUp(){
        repository= Mockito.mock(CategoriaPortOut.class);
        service.setPortOut(repository);
        categoria1=new Categoria(1,"tomates");
        categorias.add(categoria1);
    }

    @Test
    void crearCategoria() {
        try {
            Mockito.when(repository.crearCategoria(categoria1)).thenReturn(true);
            Assertions.assertTrue(service.crearCategoria(categoria1));
            Mockito.verify(repository,Mockito.times(1)).crearCategoria(categoria1);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearCategoria_NombreInvalido() {
        try {
            categoria1.setNombre("11111");
            Mockito.when(repository.crearCategoria(categoria1)).thenReturn(true);
            Assertions.assertThrows(InvalidInputException.class,()->service.crearCategoria(categoria1));
            categoria1.setNombre("           ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearCategoria(categoria1));
            Mockito.verify(repository,Mockito.times(0)).crearCategoria(categoria1);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void actualizarCategoria() {
        try {
            Mockito.when(repository.actualizarCategoria(categoria1)).thenReturn(categoria1);
            Assertions.assertEquals(categoria1,service.actualizarCategoria(categoria1));
            Mockito.verify(repository,Mockito.times(1)).actualizarCategoria(categoria1);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarCategoria_NombreInvalido() {
        try {
            categoria1.setNombre("11111");
            Mockito.when(repository.actualizarCategoria(categoria1)).thenReturn(categoria1);
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarCategoria(categoria1));
            categoria1.setNombre("           ");
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarCategoria(categoria1));
            Mockito.verify(repository,Mockito.times(0)).actualizarCategoria(categoria1);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void obtenerCategoria() {
        try {
            Mockito.when(repository.obtenerCategoriaPorId(categoria1.getId())).thenReturn(categoria1);
            Assertions.assertEquals(categoria1,service.obtenerCategoria(categoria1.getId()));
            Mockito.verify(repository,Mockito.times(1)).obtenerCategoriaPorId(categoria1.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void obtenerCategoria_CategoriaNoExiste() {
        try {
            Mockito.when(repository.obtenerCategoriaPorId(categoria1.getId())).thenThrow(SearchItemNotFoundException.class);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->service.obtenerCategoria(categoria1.getId()));
            Mockito.verify(repository,Mockito.times(1)).obtenerCategoriaPorId(categoria1.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void eliminarCategoria() {
        try {
            Mockito.when(repository.eliminarCategoria(1)).thenReturn(true);
            Assertions.assertTrue(service.eliminarCategoria(1));
            Mockito.verify(repository,Mockito.times(1)).eliminarCategoria(1);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void consultarCategorias() {
        Mockito.when(repository.consultarCategorias()).thenReturn(categorias);
        Assertions.assertEquals(categorias, service.consultarCategorias());
        Mockito.verify(repository,Mockito.times(1)).consultarCategorias();
    }
}
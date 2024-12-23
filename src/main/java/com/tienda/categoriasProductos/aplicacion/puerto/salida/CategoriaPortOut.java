package com.tienda.categoriasProductos.aplicacion.puerto.salida;

import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

import java.util.ArrayList;

public interface CategoriaPortOut {
    boolean crearCategoria(Categoria categoria)throws ItemAlreadyExistException;
    Categoria actualizarCategoria(Categoria categoria) throws SearchItemNotFoundException;
    boolean eliminarCategoria(int id) throws SearchItemNotFoundException;
    ArrayList<Categoria> consultarCategorias();
    Categoria obtenerCategoriaPorId(int id) throws SearchItemNotFoundException;
    boolean existePorId(int id);
}

package com.tienda.categoriasProductos.aplicacion.puerto.entrada;

import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;

public interface CategoriaPortIn {
    @Secured("ROLE_ADMIN")
    boolean crearCategoria(Categoria categoria)throws ItemAlreadyExistException, InvalidInputException;

    @Secured("ROLE_ADMIN")
    Categoria actualizarCategoria(Categoria categoria) throws SearchItemNotFoundException, InvalidInputException;

    Categoria obtenerCategoria(int id) throws SearchItemNotFoundException;

    @Secured("ROLE_ADMIN")
    boolean eliminarCategoria(int id) throws SearchItemNotFoundException;

    ArrayList<Categoria> consultarCategorias();
}

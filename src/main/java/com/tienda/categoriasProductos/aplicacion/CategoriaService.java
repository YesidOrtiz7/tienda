package com.tienda.categoriasProductos.aplicacion;

import com.tienda.categoriasProductos.aplicacion.puerto.entrada.CategoriaPortIn;
import com.tienda.categoriasProductos.aplicacion.puerto.salida.CategoriaPortOut;
import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoriaService implements CategoriaPortIn {
    private CategoriaPortOut portOut;
    private String regexNombreCategoria = "^(?=.*[a-zA-Z])[a-zA-Z0-9#, ]+$";
    /*------------------------------------------------------------*/
    @Autowired
    public void setPortOut(CategoriaPortOut portOut) {
        this.portOut = portOut;
    }
    /*------------------------------------------------------------*/

    @Override
    public boolean crearCategoria(Categoria categoria) throws ItemAlreadyExistException, InvalidInputException {
        /*No permitira el registro de categorias con nombres que solo contengan espacios en blanco o numeros*/
        if(categoria.getNombre().matches(regexNombreCategoria)){
            //elmina el exceso de espacios en blanco
            categoria.setNombre(categoria.getNombre().replaceAll("\\s{2,}", " "));
            return portOut.crearCategoria(categoria);
        }
        throw new InvalidInputException("Nombre de categoria con caracteres invalidos");
    }

    @Override
    public Categoria actualizarCategoria(Categoria categoria) throws SearchItemNotFoundException, InvalidInputException {
        /*No permitira el registro de categorias con nombres que solo contengan espacios en blanco o numeros*/
        if(categoria.getNombre().matches(regexNombreCategoria)){
            //elmina el exceso de espacios en blanco
            categoria.setNombre(categoria.getNombre().replaceAll("\\s{2,}", " "));
            return portOut.actualizarCategoria(categoria);
        }
        throw new InvalidInputException("Nombre de categoria con caracteres invalidos");
    }

    @Override
    public Categoria obtenerCategoria(int id) throws SearchItemNotFoundException {
        return portOut.obtenerCategoriaPorId(id);
    }

    @Override
    public boolean eliminarCategoria(int id) throws SearchItemNotFoundException {
        return portOut.eliminarCategoria(id);
    }

    @Override
    public ArrayList<Categoria> consultarCategorias() {
        return portOut.consultarCategorias();
    }
}

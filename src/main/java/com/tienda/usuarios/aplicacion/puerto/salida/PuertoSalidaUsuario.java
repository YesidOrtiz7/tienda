package com.tienda.usuarios.aplicacion.puerto.salida;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.dominio.Usuario;

import java.util.List;

public interface PuertoSalidaUsuario {
    boolean existById(int id);
    boolean existByDocumento(String documento);
    Usuario getById(int id) throws SearchItemNotFoundException;
    Usuario getByDocument(String document) throws SearchItemNotFoundException;
    Usuario crearUsuario(Usuario usuario,int rol) throws InvalidInputException, SearchItemNotFoundException, ItemAlreadyExistException;
    Usuario crearUsuario_sinCuenta(Usuario usuario, int rol) throws InvalidInputException, ItemAlreadyExistException;
    Usuario actualizarUsuario(Usuario usuario) throws InvalidInputException, ItemAlreadyExistException;
    boolean deleteById(int id) throws SearchItemNotFoundException;
    List<String> getRolesByDocument(String documento);
}

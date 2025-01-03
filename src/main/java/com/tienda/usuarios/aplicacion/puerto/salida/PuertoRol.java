package com.tienda.usuarios.aplicacion.puerto.salida;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.dominio.Rol;

import java.util.List;

public interface PuertoRol {
    Rol obtenerRolPorId(int id) throws InvalidInputException;

    Rol nuevoRol(Rol rol) throws ItemAlreadyExistException;

    boolean eliminarRol(int id) throws SearchItemNotFoundException;

    Rol actualizarRol(Rol rol) throws SearchItemNotFoundException;

    List<Rol> obtenerRoles();
}

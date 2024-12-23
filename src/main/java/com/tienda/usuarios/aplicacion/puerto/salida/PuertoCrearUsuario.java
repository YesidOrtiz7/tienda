package com.tienda.usuarios.aplicacion.puerto.salida;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.usuarios.dominio.Usuario;

public interface PuertoCrearUsuario {
    Usuario crearUsuario(Usuario usuario) throws InvalidInputException;
}

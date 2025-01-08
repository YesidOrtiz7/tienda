package com.tienda.usuarios.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

public interface CasoUsoValidarUsuario {
    boolean validarUsuarioExistePorId(int id);
    boolean validarUsuarioExistePorDocumento(String documento);
}

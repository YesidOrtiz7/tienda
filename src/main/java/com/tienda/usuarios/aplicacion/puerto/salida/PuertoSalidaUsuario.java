package com.tienda.usuarios.aplicacion.puerto.salida;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.dominio.Usuario;

public interface PuertoSalidaUsuario {
    boolean existById(int id);
    Usuario getById(int id) throws SearchItemNotFoundException;
    Usuario getByDocument(String document) throws SearchItemNotFoundException;
}

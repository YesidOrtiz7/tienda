package com.tienda.usuarios.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

public interface CasoUsoInhabilitarUsuario {
    boolean bloquear(int id,boolean habilitado) throws SearchItemNotFoundException;
}

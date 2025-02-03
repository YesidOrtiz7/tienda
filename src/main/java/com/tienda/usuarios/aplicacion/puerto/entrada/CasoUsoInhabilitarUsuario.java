package com.tienda.usuarios.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

public interface CasoUsoInhabilitarUsuario {
    void bloquear(int id,boolean habilitado) throws SearchItemNotFoundException;
}

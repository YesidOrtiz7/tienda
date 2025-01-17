package com.tienda.usuarios.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.dominio.Usuario;

public interface CasoUsoObtenerUsuario {
    Usuario obtenerPorDocumento(String documento) throws SearchItemNotFoundException;
    Usuario obtenerPorId(int id) throws SearchItemNotFoundException;
}

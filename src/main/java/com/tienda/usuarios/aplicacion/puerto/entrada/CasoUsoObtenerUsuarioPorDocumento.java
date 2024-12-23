package com.tienda.usuarios.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.dominio.Usuario;

public interface CasoUsoObtenerUsuarioPorDocumento {
    Usuario obtenerPorDocumento(String documento) throws SearchItemNotFoundException;
}

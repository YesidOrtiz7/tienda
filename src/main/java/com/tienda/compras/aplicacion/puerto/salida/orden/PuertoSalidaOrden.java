package com.tienda.compras.aplicacion.puerto.salida.orden;

import com.tienda.compras.dominio.Orden;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

public interface PuertoSalidaOrden {
    Orden registrarOrden(Orden orden) throws ItemAlreadyExistException;
    Orden actualizarOrden(Orden orden) throws SearchItemNotFoundException;
    Orden obtenerOrdenPorId(int id) throws SearchItemNotFoundException;
}

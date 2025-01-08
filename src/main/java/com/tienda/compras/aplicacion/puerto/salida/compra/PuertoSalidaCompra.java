package com.tienda.compras.aplicacion.puerto.salida.compra;

import com.tienda.compras.dominio.Compra;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

import java.util.List;

public interface PuertoSalidaCompra {
    Compra obtenerCompraPorId(int id) throws SearchItemNotFoundException;
    List<Compra> obtenerComprasPorIdUsuario(int id_usuario);
    Compra registrarCompra(Compra compra) throws ItemAlreadyExistException;
}

package com.tienda.compras.aplicacion.puerto.entrada.compra;

import com.tienda.compras.adaptador.modelo.controlador.CompraBasicData;
import com.tienda.compras.dominio.Compra;
import com.tienda.compras.dominio.Orden;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

import java.util.List;

public interface PuertoEntradaCompra {
    Compra obtenerCompraPorId(int id) throws SearchItemNotFoundException;
    List<Compra> obtenerComprasPorIdUsuario(int id_usuario) throws SearchItemNotFoundException;
    //Compra registrarCompra(Compra compra) throws ItemAlreadyExistException, SearchItemNotFoundException, InvalidInputException;
    Compra registrarCompra(int idUsuario, List<Orden> ordenes) throws ItemAlreadyExistException, SearchItemNotFoundException, InvalidInputException;
}

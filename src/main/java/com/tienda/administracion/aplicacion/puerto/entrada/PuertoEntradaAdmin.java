package com.tienda.administracion.aplicacion.puerto.entrada;

import com.tienda.administracion.dominio.Administrador;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

import java.util.ArrayList;

public interface PuertoEntradaAdmin {
    boolean existePorId(int id);
    ArrayList<Administrador> obtenerAdmins();
    Administrador obtenerAdminPorId(int id) throws SearchItemNotFoundException;
    Administrador obtenerAdminPorDocumento(String documento) throws SearchItemNotFoundException;
    String registrarAdmin(Administrador admin) throws ItemAlreadyExistException, InvalidInputException;
    Administrador actualizarAdmin(Administrador admin) throws SearchItemNotFoundException, InvalidInputException;
    boolean eliminarAdminPorId(int id) throws SearchItemNotFoundException;
}

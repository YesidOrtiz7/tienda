package com.tienda.administracion.aplicacion.puerto.salida;

import com.tienda.administracion.dominio.Administrador;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

import java.util.ArrayList;

public interface PuertoSalidaAdmin {
    boolean existById(int id);
    ArrayList<Administrador> obtenerAdmins();
    Administrador obtenerAdminPorId(int id) throws SearchItemNotFoundException;
    Administrador registrarAdmin(Administrador admin) throws ItemAlreadyExistException;
    Administrador actualizarAdmin(Administrador admin) throws SearchItemNotFoundException;
    boolean eliminarAdminPorId(int id) throws SearchItemNotFoundException;
    Administrador findByDocument(String documento) throws SearchItemNotFoundException;
}

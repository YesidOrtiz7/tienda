package com.tienda.administracion.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.data.domain.Page;

public interface PuertoEntradaAdmin {
    boolean existePorId(int id);
    Page<Usuario> obtenerAdmins(int page, int elements);
    Usuario obtenerUsuarioPorId(int id) throws SearchItemNotFoundException;
    Usuario obtenerPorDocumento(String documento) throws SearchItemNotFoundException;
    String registrarAdmin(Usuario admin) throws ItemAlreadyExistException, InvalidInputException;
    Usuario actualizarAdmin(Usuario admin) throws SearchItemNotFoundException, InvalidInputException;
    boolean eliminarAdminPorId(int id) throws SearchItemNotFoundException;
}

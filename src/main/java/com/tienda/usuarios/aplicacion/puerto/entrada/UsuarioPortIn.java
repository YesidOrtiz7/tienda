package com.tienda.usuarios.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsuarioPortIn {
    /*dejar caso uso validar usuario y caso uso inhabilitar usuario*/
    //void actualizarDatosUsuario(UsuarioDTO dto);
    String crearUsuario(Usuario usuario) throws InvalidInputException, SearchItemNotFoundException, ItemAlreadyExistException;
    void bloquear(int id,boolean habilitado) throws SearchItemNotFoundException;
    Usuario obtenerPorDocumento(String documento) throws SearchItemNotFoundException;
    Usuario obtenerPorId(int id) throws SearchItemNotFoundException;
    boolean existePorId(int id);
    boolean existByDocumento(String documento);
    boolean eliminarPorId(int id) throws SearchItemNotFoundException;
    Page<Usuario> obtenerTodos(int page, int elements);
}

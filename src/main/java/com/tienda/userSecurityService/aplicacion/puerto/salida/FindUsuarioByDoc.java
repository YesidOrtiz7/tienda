package com.tienda.userSecurityService.aplicacion.puerto.salida;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.UsuarioPersistenceModel;

import java.util.Optional;

public interface FindUsuarioByDoc {
    Optional<UsuarioPersistenceModel> findByDoc(String doc);
}

package com.tienda.userSecurityService.aplicacion.puerto.salida;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;

import java.util.Optional;

public interface FindUsuarioByDoc {
    Optional<UsuarioPersistenceModel> findByDoc(String doc);
}

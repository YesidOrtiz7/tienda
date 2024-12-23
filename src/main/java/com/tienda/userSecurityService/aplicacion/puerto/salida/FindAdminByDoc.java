package com.tienda.userSecurityService.aplicacion.puerto.salida;

import com.tienda.administracion.adaptador.modelo.AdministradorPersistenceModel;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

import java.util.Optional;

public interface FindAdminByDoc {
    Optional<AdministradorPersistenceModel> findByDoc(String doc);
}

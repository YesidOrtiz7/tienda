package com.tienda.userSecurityService.adaptador.salida;

import com.tienda.administracion.adaptador.modelo.AdministradorPersistenceModel;
import com.tienda.userSecurityService.aplicacion.puerto.salida.FindAdminByDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AdminRepositoryImpl implements FindAdminByDoc {
    private final AdminCrudRepositoryByDocQuery repository;

    @Autowired
    public AdminRepositoryImpl(AdminCrudRepositoryByDocQuery repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AdministradorPersistenceModel> findByDoc(String doc){
        return this.repository.findByDocumento(doc);
    }
}

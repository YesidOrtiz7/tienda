package com.tienda.userSecurityService.adaptador.salida;

import com.tienda.userSecurityService.aplicacion.puerto.salida.FindUsuarioByDoc;
import com.tienda.usuarios.adaptador.modelo.UsuarioPersistenceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements FindUsuarioByDoc {
    private final UsuarioCrudRepositoryByDocQuery repository;

    @Autowired
    public UsuarioRepositoryImpl(UsuarioCrudRepositoryByDocQuery repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UsuarioPersistenceModel> findByDoc(String doc) {
        return this.repository.findByDocumento(doc);
    }
}

package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository implements PuertoSalidaUsuario {
    private UsuarioCrudRepository repository;
    private MapperRepositoryToDomainUsuario mapper;

    @Autowired
    public void setRepository(UsuarioCrudRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(MapperRepositoryToDomainUsuario mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean existById(int id) {
        return repository.existsById(id);
    }

    @Override
    public Usuario getById(int id) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                this.repository.findById(id).orElseThrow(()->new SearchItemNotFoundException("No existe el usuario"))
        );
    }

    @Override
    public Usuario getByDocument(String document) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                this.repository.findByDocumento(document).orElseThrow(()->new SearchItemNotFoundException("El usuario no existe"))
        );
    }
}

package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.usuarios.aplicacion.puerto.salida.UsuariosPagSort_portOut;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class UsuariosPagSortImpl implements UsuariosPagSort_portOut {
    private final UsuariosPagSortInterface repository;
    private final MapperRepositoryToDomainUsuario mapper;

    @Autowired
    public UsuariosPagSortImpl(UsuariosPagSortInterface repository, MapperRepositoryToDomainUsuario mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<Usuario> obtenerTodos(int page, int elements) {
        return repository.findAll(PageRequest.of(page,elements))
                .map(mapper::toDomainModel);
    }
}

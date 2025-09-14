package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface UsuariosPagSortInterface extends ListPagingAndSortingRepository<UsuarioPersistenceModel,Integer> {
    @Query("SELECT u FROM UsuarioPersistenceModel u " +
            "JOIN u.usuarioRoles ur " +
            "JOIN ur.rol r " +
            "WHERE r.nombre = 'USUARIO'")
    Page<UsuarioPersistenceModel> findAllUsuarios(Pageable pageable);

    @Query("SELECT u FROM UsuarioPersistenceModel u " +
            "JOIN u.usuarioRoles ur " +
            "JOIN ur.rol r " +
            "WHERE r.nombre = 'ADMIN'")
    Page<UsuarioPersistenceModel> findAllAdministradores(Pageable pageable);
}

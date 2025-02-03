package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface UsuariosPagSortInterface extends ListPagingAndSortingRepository<UsuarioPersistenceModel,Integer> {
}

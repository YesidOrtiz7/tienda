package com.tienda.usuarios.aplicacion.puerto.salida;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioNombreApellidoDTO;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObtenerNombreUsuarioQuery_portOut extends JpaRepository<UsuarioPersistenceModel,Integer> {
    @Query("SELECT u.nombres AS nombres, u.apellidos AS apellidos FROM UsuarioPersistenceModel u WHERE u.documento = :documento")
    Optional<UsuarioNombreApellidoDTO> findNombresAndApellidosByDocumento(@Param("documento") String documento);
}

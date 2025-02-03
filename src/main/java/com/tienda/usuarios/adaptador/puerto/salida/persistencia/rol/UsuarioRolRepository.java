package com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioRolEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRolEntity,Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tbl_usuario_rol (rol_id, usuario_id) VALUES (:rolId, :usuarioId)", nativeQuery = true)
    void insertarUsuarioRol(@Param("rolId") int rolId, @Param("usuarioId") int usuarioId);
}

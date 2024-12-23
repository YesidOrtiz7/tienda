package com.tienda.usuarios.aplicacion.puerto.salida;

import com.tienda.usuarios.adaptador.modelo.UsuarioPersistenceModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InhabilitarUsuarioQuery_portOut extends JpaRepository<UsuarioPersistenceModel,Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE UsuarioPersistenceModel u SET u.habilitado = false WHERE u.id = :id")
    int bloquear(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE UsuarioPersistenceModel u SET u.habilitado = true WHERE u.id = :id")
    int desbloquear(@Param("id") int id);
}

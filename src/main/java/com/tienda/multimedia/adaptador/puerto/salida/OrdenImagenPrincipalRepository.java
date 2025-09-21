package com.tienda.multimedia.adaptador.puerto.salida;

import com.tienda.multimedia.adaptador.modelo.persistencia.ImagenProductoPersistenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenImagenPrincipalRepository extends JpaRepository<ImagenProductoPersistenceModel, Integer> {
    // Actualizar orden de una imagen
    @Modifying
    @Query("UPDATE ImagenProductoPersistenceModel i SET i.orden = :orden WHERE i.id = :id")
    void actualizarOrden(@Param("id") int id, @Param("orden") int orden);
    //void actualizarOrden(@Param("id") Long id, @Param("orden") int orden);

    // Marcar todas como no-principal para una publicación
    @Modifying
    @Query("UPDATE ImagenProductoPersistenceModel i SET i.principal = false WHERE i.publicacion.id = :publicacionId")
    void limpiarPrincipal(@Param("publicacionId") int publicacionId);
    //void limpiarPrincipal(@Param("publicacionId") Long publicacionId);

    // Establecer una imagen como principal
    @Modifying
    @Query("UPDATE ImagenProductoPersistenceModel i SET i.principal = true WHERE i.id = :id")
    void setPrincipal(@Param("id") int id);
    //void setPrincipal(@Param("id") Long id);
}

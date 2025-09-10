package com.tienda.multimedia.adaptador.modelo.persistencia;

import com.tienda.publicaciones.adaptador.modelo.PublicacionPersistenceModel;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_imagen_producto")
public class ImagenProductoPersistenceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen", nullable = false)
    private Long id;
    private String nombreArchivo;
    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private PublicacionPersistenceModel publicacion;

    public ImagenProductoPersistenceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public PublicacionPersistenceModel getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(PublicacionPersistenceModel publicacion) {
        this.publicacion = publicacion;
    }
}

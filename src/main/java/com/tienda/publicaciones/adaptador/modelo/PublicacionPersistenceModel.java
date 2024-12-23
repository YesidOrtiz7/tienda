package com.tienda.publicaciones.adaptador.modelo;

import com.tienda.categoriasProductos.adaptador.modelo.CategoriaPersistenceModel;
import com.tienda.usuarios.adaptador.modelo.UsuarioPersistenceModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="tbl_publicaciones")
public class PublicacionPersistenceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPublicacion",nullable = false)
    private int idPublicacion;
    private String tituloPublicacion;
    private String descripcion;
    private double precio;
    /*@Column(name = "categoria_id",nullable = false)
    private int categoriaId;*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id",referencedColumnName = "id_categoria")
    private CategoriaPersistenceModel categoriaId;
    private int cantidadDisponible;
    private LocalDateTime fechaPublicacion;
    /*@Column(name = "id_usuario",nullable = false)
    private int idUsuario;*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario")
    private UsuarioPersistenceModel usuario;
    @Column(name = "visible",nullable = false)
    private boolean visible;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id",referencedColumnName = "id_categoria",insertable = false,updatable = false)
    private CategoriaPersistenceModel categoria;*/

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario",insertable = false,updatable = false)
    private UsuarioPersistenceModel usuario;*/


    public PublicacionPersistenceModel() {
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getTituloPublicacion() {
        return tituloPublicacion;
    }

    public void setTituloPublicacion(String tituloPublicacion) {
        this.tituloPublicacion = tituloPublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /*public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }*/

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /*public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }*/
    /*-------------------------------------------------------*/
    public CategoriaPersistenceModel getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(CategoriaPersistenceModel categoriaId) {
        this.categoriaId = categoriaId;
    }

    /*public CategoriaPersistenceModel getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPersistenceModel categoria) {
        this.categoria = categoria;
    }*/

    public UsuarioPersistenceModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPersistenceModel usuario) {
        this.usuario = usuario;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

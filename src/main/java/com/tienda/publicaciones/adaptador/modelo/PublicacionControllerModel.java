package com.tienda.publicaciones.adaptador.modelo;

import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.multimedia.adaptador.modelo.controller.ImagenProductoControllerModel;
import com.tienda.multimedia.domino.ImagenProductoDomainModel;
import com.tienda.usuarios.adaptador.modelo.controller.UsuarioBasicData;

import java.time.LocalDateTime;
import java.util.List;

public class PublicacionControllerModel {
    private int id;
    private String tituloPublicacion;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private int cantidadDisponible;
    private LocalDateTime fechaPublicacion;
    private UsuarioBasicData usuario;
    private boolean visible;
    private List<ImagenProductoControllerModel> imagenes;

    public PublicacionControllerModel(int id, String tituloPublicacion, String descripcion, double precio, Categoria categoria, int cantidadDisponible, LocalDateTime fechaPublicacion, boolean visible) {
        this.id=id;
        this.tituloPublicacion = tituloPublicacion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaPublicacion = fechaPublicacion;
        this.visible=visible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

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

    public UsuarioBasicData getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBasicData usuario) {
        this.usuario = usuario;
    }

    public List<ImagenProductoControllerModel> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenProductoControllerModel> imagenes) {
        this.imagenes = imagenes;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

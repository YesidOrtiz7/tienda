package com.tienda.publicaciones.dominio;

import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.usuarios.dominio.Usuario;

import java.time.LocalDateTime;

public class Publicacion {
    private int id;
    private String tituloPublicacion;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private int cantidadDisponible;
    private LocalDateTime fechaPublicacion;
    private Usuario usuario;
    private boolean visible;

    public Publicacion(int id, String tituloPublicacion, String descripcion, double precio, Categoria categoria, int cantidadDisponible, LocalDateTime fechaPublicacion) {
        this.id = id;
        this.tituloPublicacion = tituloPublicacion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaPublicacion = fechaPublicacion;
    }

    public Publicacion(int id, String tituloPublicacion, String descripcion, double precio, Categoria categoria, int cantidadDisponible, LocalDateTime fechaPublicacion, Usuario usuario, boolean visible) {
        this.id = id;
        this.tituloPublicacion = tituloPublicacion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaPublicacion = fechaPublicacion;
        this.usuario = usuario;
        this.visible = visible;
    }

    public Publicacion() {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

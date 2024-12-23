package com.tienda.publicaciones.adaptador.modelo;

import com.tienda.categoriasProductos.dominio.Categoria;

import java.time.LocalDateTime;

public class PublicacionControllerModel {
    private String tituloPublicacion;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private int cantidadDisponible;
    private LocalDateTime fechaPublicacion;

    public PublicacionControllerModel(String tituloPublicacion, String descripcion, double precio, Categoria categoria, int cantidadDisponible, LocalDateTime fechaPublicacion) {
        this.tituloPublicacion = tituloPublicacion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaPublicacion = fechaPublicacion;
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
}

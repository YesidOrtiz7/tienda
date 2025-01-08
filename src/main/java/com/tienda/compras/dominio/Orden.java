package com.tienda.compras.dominio;

import com.tienda.publicaciones.dominio.Publicacion;

public class Orden {
    private int id_orden;
    private Publicacion producto;
    private int cantidad;
    private Compra compra;

    public Orden() {
    }

    public Orden(int id_orden, Publicacion producto, int cantidad, Compra compra) {
        this.id_orden = id_orden;
        this.producto = producto;
        this.cantidad = cantidad;
        this.compra = compra;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public Publicacion getProducto() {
        return producto;
    }

    public void setProducto(Publicacion producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}

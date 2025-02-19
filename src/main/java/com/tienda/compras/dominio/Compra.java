package com.tienda.compras.dominio;

import com.tienda.usuarios.dominio.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class Compra {
    private int id_compra;
    private Usuario usuario;
    private List<Orden> ordenes;
    private double total;
    private LocalDateTime fecha;

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public static double calcularTotal(int cantidad, double precio){
        return cantidad*precio;
    }
}
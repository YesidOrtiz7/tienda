package com.tienda.compras.dominio;

import com.tienda.usuarios.dominio.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class Compra {
    private int id_compra;
    private Usuario id_usuario;
    private List<Orden> id_orden;
    private double total;
    private LocalDateTime fecha;

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<Orden> getId_orden() {
        return id_orden;
    }

    public void setId_orden(List<Orden> id_orden) {
        this.id_orden = id_orden;
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
package com.tienda.compras.adaptador.modelo.controlador;

import com.tienda.compras.dominio.Orden;
import com.tienda.usuarios.dominio.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class CompraControllerModel {
    private int usuario;
    private List<Orden> ordenes;
    private double total;
    private LocalDateTime fecha;

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
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
}

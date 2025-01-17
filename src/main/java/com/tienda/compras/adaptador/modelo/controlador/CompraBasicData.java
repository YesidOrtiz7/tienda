package com.tienda.compras.adaptador.modelo.controlador;

import com.tienda.compras.dominio.Orden;

import java.util.List;

public class CompraBasicData {
    private int usuario;
    private List<Orden> ordenes;

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
}

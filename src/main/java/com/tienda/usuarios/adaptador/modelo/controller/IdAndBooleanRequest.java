package com.tienda.usuarios.adaptador.modelo.controller;

public class IdAndBooleanRequest extends IdRequest {
    private boolean habilitado;

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
}

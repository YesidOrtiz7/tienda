package com.tienda.multimedia.adaptador.modelo.controller.ordenImagen;

public class ImagenDataControllerModel {
    private int id;
    private int orden;
    private boolean principal;

    public ImagenDataControllerModel(int id, int orden, boolean principal) {
        this.id = id;
        this.orden = orden;
        this.principal = principal;
    }

    public ImagenDataControllerModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }
}

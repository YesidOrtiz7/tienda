package com.tienda.multimedia.adaptador.modelo.controller;

public class ImagenProductoControllerModel {
    private int id;
    private String nombreArchivo;
    private boolean principal;
    private int orden;

    public ImagenProductoControllerModel(int id, String nombreArchivo) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
    }

    public ImagenProductoControllerModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}

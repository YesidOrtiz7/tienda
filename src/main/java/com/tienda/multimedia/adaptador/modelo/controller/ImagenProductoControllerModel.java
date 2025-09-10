package com.tienda.multimedia.adaptador.modelo.controller;

public class ImagenProductoControllerModel {
    private int id;
    private String nombreArchivo;

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
}

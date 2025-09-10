package com.tienda.multimedia.domino;

public class ImagenProductoDomainModel {
    private int id;
    private String nombreArchivo;
    private int idPublicacion;

    public ImagenProductoDomainModel() {
    }

    public ImagenProductoDomainModel(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public ImagenProductoDomainModel(String nombreArchivo, int idPublicacion){
        this.nombreArchivo=nombreArchivo;

        this.idPublicacion=idPublicacion;
    }

    public ImagenProductoDomainModel(int id, String nombreArchivo, int idPublicacion) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.idPublicacion = idPublicacion;
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

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }
}

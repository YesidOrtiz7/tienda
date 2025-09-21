package com.tienda.multimedia.adaptador.modelo.controller.ordenImagen;

import java.util.List;

public class ImagenOrdenControllerModel {
    private int idPublicacion;
    private List<ImagenDataControllerModel> imagenDataList;

    public ImagenOrdenControllerModel(int idPublicacion, List<ImagenDataControllerModel> imagenDataList) {
        this.idPublicacion = idPublicacion;
        this.imagenDataList = imagenDataList;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public List<ImagenDataControllerModel> getImagenDataList() {
        return imagenDataList;
    }

    public void setImagenDataList(List<ImagenDataControllerModel> imagenDataList) {
        this.imagenDataList = imagenDataList;
    }
}


package com.tienda.multimedia.domino.ordenImagen;

import java.util.List;

public class ImagenOrdenDomainModel {
    private int idPublicacion;
    private List<ImagenDataDomainModel> imagenDataList;

    public ImagenOrdenDomainModel(int idPublicacion, List<ImagenDataDomainModel> imagenDataList) {
        this.idPublicacion = idPublicacion;
        this.imagenDataList = imagenDataList;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public List<ImagenDataDomainModel> getImagenDataList() {
        return imagenDataList;
    }

    public void setImagenDataList(List<ImagenDataDomainModel> imagenDataList) {
        this.imagenDataList = imagenDataList;
    }
}

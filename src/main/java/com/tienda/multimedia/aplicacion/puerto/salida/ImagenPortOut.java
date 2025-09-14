package com.tienda.multimedia.aplicacion.puerto.salida;

import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.multimedia.domino.ImagenProductoDomainModel;

import java.util.List;

public interface ImagenPortOut {
    List<ImagenProductoDomainModel> consultarImagenesPorPublicacion(int idPublicacion);
    ImagenProductoDomainModel consultarImagenPorId(int idImagen) throws SearchItemNotFoundException;
    ImagenProductoDomainModel consultarImagenPorNombre(String nombre) throws SearchItemNotFoundException;
    boolean imagenPorNombreExiste(String nombre) throws SearchItemNotFoundException;
    boolean guardarImagen(ImagenProductoDomainModel imagen, int idPublicacion) throws ItemAlreadyExistException, SearchItemNotFoundException;
    boolean guardarImagen(String imagen, int idPublicacion) throws ItemAlreadyExistException, SearchItemNotFoundException;
    boolean eliminarImagenPorId(int idImagen) throws SearchItemNotFoundException;
    boolean imagenExiste(int idImagen);
}
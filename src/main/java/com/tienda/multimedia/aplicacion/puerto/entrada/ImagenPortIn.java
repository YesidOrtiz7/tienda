package com.tienda.multimedia.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.multimedia.domino.ImagenProductoDomainModel;
import com.tienda.multimedia.domino.ordenImagen.ImagenOrdenDomainModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImagenPortIn {
    List<ImagenProductoDomainModel> consultarImagenesPorPublicacion(int idPublicacion);
    Resource consultarImagenPorId(int idImagen) throws SearchItemNotFoundException;
    Resource consultarImagenPorNombre(String nombre) throws SearchItemNotFoundException;
    boolean guardarImagenes(List<MultipartFile> imagenes, int idPublicacion) throws ItemAlreadyExistException, SearchItemNotFoundException;
    boolean eliminarImagenPorId(int idImagen) throws SearchItemNotFoundException;
    boolean imagenExiste(int idImagen);
    boolean actualizarOrden(ImagenOrdenDomainModel imagenes) throws SearchItemNotFoundException;
}

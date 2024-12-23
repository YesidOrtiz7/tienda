package com.tienda.publicaciones.aplicacion.puerto.salida;

import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.publicaciones.dominio.Publicacion;

import java.util.ArrayList;

public interface PublicacionPortOut {
    ArrayList<Publicacion> obtenerPublicaciones();
    Publicacion obtenerPublicacion(int id) throws SearchItemNotFoundException;
    //boolean existePublicacion(int id);
    Publicacion crearPublicacion(Publicacion publicacion) throws ItemAlreadyExistException, SearchItemNotFoundException;
    Publicacion actualizarPublicacion(Publicacion publicacion) throws SearchItemNotFoundException;
    boolean eliminarPublicacion(int id) throws SearchItemNotFoundException;
}

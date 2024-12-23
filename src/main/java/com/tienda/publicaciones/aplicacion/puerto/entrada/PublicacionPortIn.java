package com.tienda.publicaciones.aplicacion.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.publicaciones.dominio.Publicacion;

import java.util.ArrayList;

public interface PublicacionPortIn {
    ArrayList<Publicacion> obtenerPublicaciones();
    Publicacion obtenerPublicacion(int id) throws SearchItemNotFoundException;
    Publicacion crearPublicacion(Publicacion publicacion) throws InvalidInputException, ItemAlreadyExistException, SearchItemNotFoundException;
    Publicacion actualizarPublicacion(Publicacion publicacion) throws InvalidInputException, SearchItemNotFoundException;
    boolean eliminarPublicacion(int id) throws SearchItemNotFoundException;
}
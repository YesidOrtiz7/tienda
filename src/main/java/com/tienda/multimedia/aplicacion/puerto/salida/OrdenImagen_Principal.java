package com.tienda.multimedia.aplicacion.puerto.salida;

public interface OrdenImagen_Principal {
    boolean actualizarOrden(int id, int orden);
    boolean limpiarPrincipal(int idPublicacion);
    boolean setPrincipal(int id);
}

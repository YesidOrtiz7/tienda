package com.tienda.multimedia.adaptador.puerto.salida;

import com.tienda.multimedia.aplicacion.puerto.salida.OrdenImagen_Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdenImagenPrincipalRepositoryAdapter implements OrdenImagen_Principal {

    private OrdenImagenPrincipalRepository repository;

    /*---------------------------------------------------------------------------------*/
    @Autowired
    public void setRepository(OrdenImagenPrincipalRepository repository) {
        this.repository = repository;
    }
    /*---------------------------------------------------------------------------------*/

    @Override
    public boolean actualizarOrden(int id, int orden) {
        repository.actualizarOrden(id, orden);
        return true;
    }

    @Override
    public boolean limpiarPrincipal(int idPublicacion) {
        repository.limpiarPrincipal(idPublicacion);
        return true;
    }

    @Override
    public boolean setPrincipal(int id) {
        repository.setPrincipal(id);
        return true;
    }
}

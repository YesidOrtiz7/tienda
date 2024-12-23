package com.tienda.publicaciones.aplicacion;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.publicaciones.aplicacion.puerto.entrada.PublicacionPortIn;
import com.tienda.publicaciones.aplicacion.puerto.salida.PublicacionPortOut;
import com.tienda.publicaciones.dominio.Publicacion;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoObtenerUsuarioPorDocumento;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoValidarUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class PublicacionService implements PublicacionPortIn {
    private PublicacionPortOut portOut;
    private CasoUsoValidarUsuario validacionUsuario;
    private CasoUsoObtenerUsuarioPorDocumento obtenerUsuarioPorDocumento;
    private final String regexTituloPublicacion = "^(?=.*[a-zA-Z])[a-zA-Z0-9#, ]+$";

    /*------------------------------------------------------------*/
    @Autowired
    public void setPortOut(PublicacionPortOut portOut) {
        this.portOut = portOut;
    }

    @Autowired
    public void setValidacionUsuario(CasoUsoValidarUsuario validacionUsuario) {
        this.validacionUsuario = validacionUsuario;
    }

    @Autowired
    public void setObtenerUsuarioPorDocumento(CasoUsoObtenerUsuarioPorDocumento obtenerUsuarioPorDocumento) {
        this.obtenerUsuarioPorDocumento = obtenerUsuarioPorDocumento;
    }
    /*------------------------------------------------------------*/

    @Override
    public ArrayList<Publicacion> obtenerPublicaciones() {
        return portOut.obtenerPublicaciones();
    }

    @Override
    public Publicacion obtenerPublicacion(int id) throws SearchItemNotFoundException {
        return portOut.obtenerPublicacion(id);
    }

    @Override
    public Publicacion crearPublicacion(Publicacion publicacion) throws InvalidInputException, ItemAlreadyExistException, SearchItemNotFoundException {

        if (publicacion.getCantidadDisponible()<=0){
            throw new InvalidInputException("La cantidad de unidades disponibles debe ser mayor a cero");
        }
        if (publicacion.getPrecio()<0){
            throw new InvalidInputException("El precio del producto no puede ser menor a cero");
        }
        if (publicacion.getDescripcion().isBlank()){
            throw new InvalidInputException("La descripcion de la publicacion no puede estar en blanco");
        }
        if (!publicacion.getTituloPublicacion().matches(regexTituloPublicacion)){
            throw new InvalidInputException("Titulo de la publicacion con caracteres invalidos");
        }
        if (publicacion.getUsuario().getId()<=0 && !publicacion.getUsuario().getDocumento().isBlank()){
            publicacion.setUsuario(obtenerUsuarioPorDocumento.obtenerPorDocumento(publicacion.getUsuario().getDocumento()));
        }
        if (!validacionUsuario.validarUsuarioExiste(publicacion.getUsuario().getId())){
            throw new InvalidInputException("El usuario que esta tratando de crear la publicacion no existe");
        }
        publicacion.setTituloPublicacion(publicacion.getTituloPublicacion().replaceAll("\\s{2,}", " "));
        /*la aplicacion asigna la fecha de publicacion para evitar conflictos con la entrada ingresada por el
         * usuario*/
        publicacion.setFechaPublicacion(LocalDateTime.now());
        publicacion.setVisible(true);
        return portOut.crearPublicacion(publicacion);
    }

    @Override
    public Publicacion actualizarPublicacion(Publicacion publicacion) throws InvalidInputException, SearchItemNotFoundException {
        if (publicacion.getCantidadDisponible()<=0){
            throw new InvalidInputException("La cantidad de unidades disponibles debe ser mayor a cero");
        }
        if (publicacion.getPrecio()<0){
            throw new InvalidInputException("El precio del producto no puede ser menor a cero");
        }
        if (publicacion.getDescripcion().isBlank()){
            throw new InvalidInputException("La descripcion de la publicacion no puede estar en blanco");
        }
        if (!publicacion.getTituloPublicacion().matches(regexTituloPublicacion)){
            throw new InvalidInputException("Titulo de la publicacion con caracteres invalidos");
        }
        if (publicacion.getUsuario().getId()<=0 && !publicacion.getUsuario().getDocumento().isBlank()){
            publicacion.setUsuario(obtenerUsuarioPorDocumento.obtenerPorDocumento(publicacion.getUsuario().getDocumento()));
        }
        if (!validacionUsuario.validarUsuarioExiste(publicacion.getUsuario().getId())){
            throw new InvalidInputException("El usuario que esta tratando de crear la publicacion no existe");
        }
        publicacion.setTituloPublicacion(publicacion.getTituloPublicacion().replaceAll("\\s{2,}", " "));
        //publicacion.setFechaPublicacion(LocalDateTime.now());
        return portOut.actualizarPublicacion(publicacion);
    }

    @Override
    public boolean eliminarPublicacion(int id) throws SearchItemNotFoundException {
        return portOut.eliminarPublicacion(id);
    }
}

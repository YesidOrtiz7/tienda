package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoInhabilitarUsuario;
import com.tienda.usuarios.aplicacion.puerto.salida.InhabilitarUsuarioQuery_portOut;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class ServicioInhabilitarUsuario implements CasoUsoInhabilitarUsuario {
    private PuertoSalidaUsuario repository;
    private InhabilitarUsuarioQuery_portOut queryPortOut;

    @Autowired
    public ServicioInhabilitarUsuario(PuertoSalidaUsuario repository, InhabilitarUsuarioQuery_portOut queryPortOut) {
        this.repository = repository;
        this.queryPortOut = queryPortOut;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public boolean bloquear(int id, boolean habilitar) throws SearchItemNotFoundException {
        if (!repository.existById(id)){
            throw new SearchItemNotFoundException("El usuario no existe");
        }
        if (habilitar){
            queryPortOut.desbloquear(id);
        }
        queryPortOut.bloquear(id);
        return true;
    }
}

package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoObtenerUsuario;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioObtenerUsuario implements CasoUsoObtenerUsuario {
    private final PuertoSalidaUsuario repository;

    @Autowired
    public ServicioObtenerUsuario(PuertoSalidaUsuario repository) {
        this.repository = repository;
    }

    @Override
    public Usuario obtenerPorDocumento(String documento) throws SearchItemNotFoundException {
        return this.repository.getByDocument(documento);
    }

    @Override
    public Usuario obtenerPorId(int id) throws SearchItemNotFoundException {
        return this.repository.getById(id);
    }
}

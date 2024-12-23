package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoValidarUsuario;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioValidarUsuario implements CasoUsoValidarUsuario {
    private PuertoSalidaUsuario usuarioRepository;

    @Autowired
    public void setUsuarioRepository(PuertoSalidaUsuario usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean validarUsuarioExiste(int id) {
        return this.usuarioRepository.existById(id);
    }
}

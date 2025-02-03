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
    public boolean validarUsuarioExistePorId(int id) {
        return this.usuarioRepository.existById(id);
    }

    @Override
    public boolean validarUsuarioExistePorDocumento(String documento){
        return this.usuarioRepository.existByDocumento(documento);
    }
}

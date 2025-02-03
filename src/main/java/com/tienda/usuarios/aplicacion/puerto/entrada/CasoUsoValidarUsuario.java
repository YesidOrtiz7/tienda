package com.tienda.usuarios.aplicacion.puerto.entrada;

public interface CasoUsoValidarUsuario {
    boolean validarUsuarioExistePorId(int id);
    boolean validarUsuarioExistePorDocumento(String documento);
}

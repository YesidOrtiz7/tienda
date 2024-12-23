package com.tienda.usuarios.adaptador.modelo;

public class CrearUsuario_ControllerModel extends UsuarioBasicData{
    private String contrasena;

    public CrearUsuario_ControllerModel() {
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

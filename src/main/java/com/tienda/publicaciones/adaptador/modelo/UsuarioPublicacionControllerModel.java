package com.tienda.publicaciones.adaptador.modelo;

public class UsuarioPublicacionControllerModel {
    private int id;
    private String nombres;
    private String apellidos;

    public UsuarioPublicacionControllerModel(int id, String nombres, String apellidos) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public UsuarioPublicacionControllerModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}

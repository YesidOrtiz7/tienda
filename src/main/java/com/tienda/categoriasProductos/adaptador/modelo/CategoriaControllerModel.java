package com.tienda.categoriasProductos.adaptador.modelo;

public class CategoriaControllerModel {
    private String nombre;

    public CategoriaControllerModel(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaControllerModel() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

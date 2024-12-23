package com.tienda.categoriasProductos.adaptador.modelo;

import jakarta.persistence.*;

@Entity
@Table(name="tbl_categorias")
public class CategoriaPersistenceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria",nullable = false)
    private int id;
    private String nombre;


    public CategoriaPersistenceModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

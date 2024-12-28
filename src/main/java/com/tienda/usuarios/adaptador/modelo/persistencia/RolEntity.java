package com.tienda.usuarios.adaptador.modelo.persistencia;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbl_roles")
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @OneToMany(mappedBy = "rol",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UsuarioRolEntity> usuarioRoles;

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

    public List<UsuarioRolEntity> getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(List<UsuarioRolEntity> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }
}

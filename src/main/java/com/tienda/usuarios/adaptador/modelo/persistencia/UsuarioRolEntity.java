package com.tienda.usuarios.adaptador.modelo.persistencia;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_usuario_rol")
public class UsuarioRolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id",nullable = false)
    private UsuarioPersistenceModel usuario;

    @ManyToOne
    @JoinColumn(name = "rol_id",nullable = false)
    private RolEntity rol;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioPersistenceModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPersistenceModel usuario) {
        this.usuario = usuario;
    }

    public RolEntity getRol() {
        return rol;
    }

    public void setRol(RolEntity rol) {
        this.rol = rol;
    }
}

package com.tienda.administracion.adaptador.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_administradores")
public class AdministradorPersistenceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin", nullable = false)
    private int id;

    @Column(nullable = false,length = 12)
    private String documento;

    @Column(nullable = false,length = 60)
    private String nombres;

    @Column(nullable = false,length = 60)
    private String apellidos;

    @Column(nullable = false,length = 120)
    private String contrasena;

    @Column(nullable = false,length = 120)
    private String totpSecret;

    @Column(nullable = false)
    private boolean habilitado;

    public AdministradorPersistenceModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getTotpSecret() {
        return totpSecret;
    }

    public void setTotpSecret(String totpSecret) {
        this.totpSecret = totpSecret;
    }

    public String getUserName(){
        return this.nombres+" "+ this.apellidos;
    }
}

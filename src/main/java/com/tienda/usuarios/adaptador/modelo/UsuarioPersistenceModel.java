package com.tienda.usuarios.adaptador.modelo;

import jakarta.persistence.*;


@Entity
@Table(name = "tbl_usuarios")
public class UsuarioPersistenceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private int id;

    @Column(nullable = false,length = 12)
    private String documento;

    @Column(nullable = false,length = 60)
    private String primerNombre;

    @Column(nullable = false,length = 60)
    private String segundoNombre;

    @Column(nullable = false,length = 60)
    private String primerApellido;

    @Column(nullable = false,length = 60)
    private String segundoApellido;

    @Column(nullable = false,length = 120)
    private String contrasena;

    @Column(nullable = false,length = 120)
    private String totpSecret;

    @Column(nullable = false)
    private boolean habilitado;

    @Column(nullable = false)
    private int saldoEnCuenta;

    public UsuarioPersistenceModel() {
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

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
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

    public int getSaldoEnCuenta() {
        return saldoEnCuenta;
    }

    public void setSaldoEnCuenta(int saldoEnCuenta) {
        this.saldoEnCuenta = saldoEnCuenta;
    }

    public String getTotpSecret() {
        return totpSecret;
    }

    public void setTotpSecret(String totpSecret) {
        this.totpSecret = totpSecret;
    }

    public String getUserName(){
        return this.primerNombre+" "+ this.segundoNombre+" "+ this.primerApellido+" "+ this.segundoApellido;
    }
}

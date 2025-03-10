package com.tienda.usuarios.adaptador.modelo.persistencia;

import com.tienda.compras.adaptador.modelo.persistencia.CompraEntity;
import com.tienda.cuentas.adaptador.puerto.salida.modelos.CuentaEntity;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "tbl_usuarios")
public class UsuarioPersistenceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false, unique = true)
    private int id;

    @Column(nullable = false,length = 12, unique = true)
    private String documento;

    @Column(nullable = false,length = 120)
    private String correo;

    @Column(nullable = false,length = 120)
    private String nombres;

    @Column(nullable = false,length = 120)
    private String apellidos;

    @Column(nullable = false,length = 60)
    private String telefono;

    @Column(nullable = false,length = 120)
    private String contrasena;

    @Column(nullable = false,length = 120)
    private String totpSecret;

    @Column(nullable = false)
    private boolean bloqueado;

    @Column(nullable = false)
    private boolean eliminado;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UsuarioRolEntity> usuarioRoles;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private CuentaEntity cuenta;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CompraEntity> compras;

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

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getTotpSecret() {
        return totpSecret;
    }

    public void setTotpSecret(String totpSecret) {
        this.totpSecret = totpSecret;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUserName(){
        return this.nombres+" "+ this.apellidos;
    }

    public List<UsuarioRolEntity> getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(List<UsuarioRolEntity> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }

    public CuentaEntity getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaEntity cuenta) {
        this.cuenta = cuenta;
    }

    public List<CompraEntity> getCompras() {
        return compras;
    }

    public void setCompras(List<CompraEntity> compras) {
        this.compras = compras;
    }
}

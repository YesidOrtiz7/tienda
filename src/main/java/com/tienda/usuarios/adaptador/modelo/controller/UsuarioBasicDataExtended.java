package com.tienda.usuarios.adaptador.modelo.controller;

import com.tienda.cuentas.dominio.Cuenta;
import com.tienda.usuarios.dominio.Rol;

import java.util.List;

public class UsuarioBasicDataExtended extends UsuarioBasicData{
    private boolean bloqueado;
    private boolean eliminado;
    private List<Rol> roles;
    private Cuenta cuenta;

    public UsuarioBasicDataExtended() {
        super();
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}

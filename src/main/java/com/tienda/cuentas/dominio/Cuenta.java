package com.tienda.cuentas.dominio;

public class Cuenta {
    private int id_usuario;
    private double saldo;

    public Cuenta() {
    }

    public Cuenta(int id_usuario, double saldo) {
        this.id_usuario = id_usuario;
        this.saldo = saldo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}

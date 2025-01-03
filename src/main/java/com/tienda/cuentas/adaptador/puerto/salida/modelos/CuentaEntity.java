package com.tienda.cuentas.adaptador.puerto.salida.modelos;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_cuenta")
public class CuentaEntity {
    @Id
    @Column(nullable = false, unique = true)
    private int id_usuario;

    @Column(nullable = false)
    private double saldo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario")
    private UsuarioPersistenceModel usuario;

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

    public UsuarioPersistenceModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPersistenceModel usuario) {
        this.usuario = usuario;
    }
}

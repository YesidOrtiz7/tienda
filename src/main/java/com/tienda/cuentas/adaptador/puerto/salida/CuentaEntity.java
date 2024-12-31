package com.tienda.cuentas.adaptador.puerto.salida;

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

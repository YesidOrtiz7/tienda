package com.tienda.compras.adaptador.modelo.persistencia;

import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_compras")
public class CompraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "id_compra",unique = true)
    private int id_compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private UsuarioPersistenceModel usuario;

    @OneToMany(mappedBy = "compra_id", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<OrdenEntity> orden_id;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private LocalDateTime fecha;

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public UsuarioPersistenceModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPersistenceModel usuario) {
        this.usuario = usuario;
    }

    public List<OrdenEntity> getOrden_id() {
        return orden_id;
    }

    public void setOrden_id(List<OrdenEntity> orden_id) {
        this.orden_id = orden_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

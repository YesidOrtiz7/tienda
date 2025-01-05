package com.tienda.compras.adaptador.modelo.persistencia;

import com.tienda.publicaciones.adaptador.modelo.PublicacionPersistenceModel;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_orden")
public class OrdenEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id_orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id",referencedColumnName = "idPublicacion",nullable = false)
    private PublicacionPersistenceModel publicacion;

    @Column(nullable = false)
    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id",referencedColumnName = "id_compra",nullable = false)
    private CompraEntity compra_id;

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public PublicacionPersistenceModel getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(PublicacionPersistenceModel publicacion) {
        this.publicacion = publicacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public CompraEntity getCompra_id() {
        return compra_id;
    }

    public void setCompra_id(CompraEntity compra_id) {
        this.compra_id = compra_id;
    }
}

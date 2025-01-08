package com.tienda.compras.adaptador.puerto.salida.persistencia.compra;

import com.tienda.compras.adaptador.modelo.persistencia.CompraEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompraCrudRepository extends CrudRepository<CompraEntity,Integer> {
    List<CompraEntity> findByUsuario_id(int usuarioId);
}

package com.tienda.usuarios.aplicacion.puerto.salida;

import com.tienda.usuarios.dominio.Usuario;
import org.springframework.data.domain.Page;

public interface UsuariosPagSort_portOut{
    Page<Usuario> obtenerTodos(int page, int elements);
}

package com.tienda.compras.aplicacion.servicio.compra;

import com.tienda.compras.aplicacion.puerto.entrada.compra.PuertoEntradaCompra;
import com.tienda.compras.aplicacion.puerto.salida.compra.PuertoSalidaCompra;
import com.tienda.compras.dominio.Compra;
import com.tienda.compras.dominio.Orden;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoObtenerUsuario;
import com.tienda.usuarios.aplicacion.servicio.ServicioValidarUsuario;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService implements PuertoEntradaCompra {
    private PuertoSalidaCompra repository;
    private ServicioValidarUsuario validarUsuario;
    private CasoUsoObtenerUsuario obtenerUsuario;

    public CompraService() {
    }

    @Autowired
    public CompraService(PuertoSalidaCompra repository, ServicioValidarUsuario validarUsuario, CasoUsoObtenerUsuario obtenerUsuario) {
        this.repository = repository;
        this.validarUsuario = validarUsuario;
        this.obtenerUsuario = obtenerUsuario;
    }

    @Override
    public Compra obtenerCompraPorId(int id) throws SearchItemNotFoundException {
        return repository.obtenerCompraPorId(id);
    }

    @Override
    public List<Compra> obtenerComprasPorIdUsuario(int id_usuario) throws SearchItemNotFoundException {
        if (!validarUsuario.validarUsuarioExistePorId(id_usuario)){
            throw new SearchItemNotFoundException("No existe el usuario");
        }
        return repository.obtenerComprasPorIdUsuario(id_usuario);
    }

    @Override
    public Compra registrarCompra(int idUsuario, List<Orden> ordenes) throws ItemAlreadyExistException, SearchItemNotFoundException, InvalidInputException {
        if (!validarUsuario.validarUsuarioExistePorId(idUsuario)){
            throw new SearchItemNotFoundException("No se encontro el usuario");
        }
        if (ordenes.isEmpty()){
            throw new InvalidInputException("No hay articulos en esta compra");
        }
        Compra compra=new Compra();
        Usuario usuario=obtenerUsuario.obtenerPorId(idUsuario);
        compra.setUsuario(usuario);

        double totalCompra=0;
        for (Orden i : ordenes){
            totalCompra+=calcularTotalItem(i.getCantidad(),i.getProducto().getPrecio());
        }
        compra.setOrdenes(ordenes);
        compra.setTotal(totalCompra);
        compra.setFecha(LocalDateTime.now());
        return repository.registrarCompra(compra);
    }
    private static double calcularTotalItem(int cantidad, double valor){
        return cantidad*valor;
    }
}

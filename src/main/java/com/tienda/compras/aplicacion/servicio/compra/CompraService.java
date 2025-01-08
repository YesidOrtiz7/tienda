package com.tienda.compras.aplicacion.servicio.compra;

import com.tienda.compras.aplicacion.puerto.entrada.compra.PuertoEntradaCompra;
import com.tienda.compras.aplicacion.puerto.salida.compra.PuertoSalidaCompra;
import com.tienda.compras.dominio.Compra;
import com.tienda.compras.dominio.Orden;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.servicio.ServicioValidarUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService implements PuertoEntradaCompra {
    private PuertoSalidaCompra repository;
    private ServicioValidarUsuario validarUsuario;

    public CompraService() {
    }

    @Autowired
    public CompraService(PuertoSalidaCompra repository, ServicioValidarUsuario validarUsuario) {
        this.repository = repository;
        this.validarUsuario = validarUsuario;
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
    public Compra registrarCompra(Compra compra) throws ItemAlreadyExistException, SearchItemNotFoundException, InvalidInputException {
        if (!validarUsuario.validarUsuarioExistePorDocumento(compra.getUsuario().getDocumento())){
            throw new SearchItemNotFoundException("No se encontro el usuario");
        }
        if (compra.getOrdenes().isEmpty()){
            throw new InvalidInputException("No hay articulos en esta compra");
        }
        double totalCompra=0;
        for (Orden i : compra.getOrdenes()){
            totalCompra+=calcularTotalItem(i.getCantidad(),i.getProducto().getPrecio());
        }
        compra.setTotal(totalCompra);
        compra.setFecha(LocalDateTime.now());
        return repository.registrarCompra(compra);
    }
    private static double calcularTotalItem(int cantidad, double valor){
        return cantidad*valor;
    }
}

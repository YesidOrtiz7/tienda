package com.tienda.compras.adaptador.puerto.salida.persistencia.compra;

import com.tienda.compras.aplicacion.puerto.salida.compra.PuertoSalidaCompra;
import com.tienda.compras.dominio.Compra;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompraRepository implements PuertoSalidaCompra {
    private MapperRepositoryToDomainCompra mapper;
    private CompraCrudRepository repository;

    public CompraRepository() {
    }

    public CompraRepository(MapperRepositoryToDomainCompra mapper, CompraCrudRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Compra obtenerCompraPorId(int id) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                repository.findById(id).orElseThrow(()->new SearchItemNotFoundException("No existe la compra"))
        );
    }

    @Override
    public List<Compra> obtenerComprasPorIdUsuario(int id_usuario) {
        List<Compra> response=new ArrayList<>();
        repository.findByUsuario_id(id_usuario).forEach(
                (i)->{response.add(mapper.toDomainModel(i));}
        );
        return response;
    }

    @Override
    public Compra registrarCompra(Compra compra) throws ItemAlreadyExistException {
        if (repository.existsById(compra.getId_compra())){
            throw new ItemAlreadyExistException("Ya existe la compra");
        }
        return mapper.toDomainModel(
                repository.save(mapper.toPersistenceModel(compra))
        );
    }
}

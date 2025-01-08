package com.tienda.compras.adaptador.puerto.salida.persistencia.orden;

import com.tienda.compras.aplicacion.puerto.salida.orden.PuertoSalidaOrden;
import com.tienda.compras.dominio.Orden;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class OrdenRepository implements PuertoSalidaOrden {
    private OrdenCrudRepository repository;
    private MapperRepositoryToDomainOrden mapper;

    public OrdenRepository() {
    }

    public OrdenRepository(OrdenCrudRepository repository, MapperRepositoryToDomainOrden mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Orden registrarOrden(Orden orden) throws ItemAlreadyExistException {
        if (repository.existsById(orden.getId_orden())){
            throw new ItemAlreadyExistException("La orden ya existe");
        }
        return mapper.toDomainModel(
                repository.save(mapper.toPersistenceModel(orden))
        );
    }

    @Override
    public Orden actualizarOrden(Orden orden) throws SearchItemNotFoundException {
        if (!repository.existsById(orden.getId_orden())){
            throw new SearchItemNotFoundException("La orden no existe");
        }
        return mapper.toDomainModel(
                repository.save(mapper.toPersistenceModel(orden))
        );
    }

    @Override
    public Orden obtenerOrdenPorId(int id) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                repository.findById(id).orElseThrow(()->new SearchItemNotFoundException("La orden no existe"))
        );
    }
}

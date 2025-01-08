package com.tienda.compras.adaptador.puerto.salida.persistencia.orden;

import com.tienda.compras.adaptador.modelo.persistencia.OrdenEntity;
import com.tienda.compras.dominio.Orden;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;

class OrdenRepositoryTest {
    private OrdenEntity persistenceModel=new OrdenEntity();
    private Orden domainModel=new Orden();
    private OrdenCrudRepository interfaceRepository=mock(OrdenCrudRepository.class);
    private MapperRepositoryToDomainOrden mapper=mock(MapperRepositoryToDomainOrden.class);

    private OrdenRepository repository;

    @BeforeEach
    void setUp() {
        persistenceModel.setId_orden(1);
        persistenceModel.setCantidad(5);

        domainModel.setId_orden(1);
        domainModel.setCantidad(5);

        repository=new OrdenRepository(interfaceRepository,mapper);
    }

    @Test
    void registrarOrden() {
        try {
            when(interfaceRepository.existsById(persistenceModel.getId_orden())).thenReturn(false);
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(mapper.toPersistenceModel(domainModel)).thenReturn(persistenceModel);
            when(interfaceRepository.save(persistenceModel)).thenReturn(persistenceModel);
            assertEquals(domainModel,repository.registrarOrden(domainModel));
            verify(interfaceRepository,times(1)).existsById(persistenceModel.getId_orden());
            verify(mapper,times(1)).toDomainModel(persistenceModel);
            verify(mapper,times(1)).toPersistenceModel(domainModel);
            verify(interfaceRepository,times(1)).save(persistenceModel);

        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarOrden_OrdenYaExiste() {
        try {
            when(interfaceRepository.existsById(persistenceModel.getId_orden())).thenReturn(true);
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(mapper.toPersistenceModel(domainModel)).thenReturn(persistenceModel);
            when(interfaceRepository.save(persistenceModel)).thenReturn(persistenceModel);
            assertThrows(ItemAlreadyExistException.class,()->repository.registrarOrden(domainModel));
            verify(interfaceRepository,times(1)).existsById(persistenceModel.getId_orden());
            verify(mapper,times(0)).toDomainModel(persistenceModel);
            verify(mapper,times(0)).toPersistenceModel(domainModel);
            verify(interfaceRepository,times(0)).save(persistenceModel);

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void actualizarOrden() {
        try {
            when(interfaceRepository.existsById(persistenceModel.getId_orden())).thenReturn(true);
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(mapper.toPersistenceModel(domainModel)).thenReturn(persistenceModel);
            when(interfaceRepository.save(persistenceModel)).thenReturn(persistenceModel);
            assertEquals(domainModel,repository.actualizarOrden(domainModel));
            verify(interfaceRepository,times(1)).existsById(persistenceModel.getId_orden());
            verify(mapper,times(1)).toDomainModel(persistenceModel);
            verify(mapper,times(1)).toPersistenceModel(domainModel);
            verify(interfaceRepository,times(1)).save(persistenceModel);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void actualizarOrden_OrdenNoExiste() {
        try {
            when(interfaceRepository.existsById(persistenceModel.getId_orden())).thenReturn(false);
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(mapper.toPersistenceModel(domainModel)).thenReturn(persistenceModel);
            when(interfaceRepository.save(persistenceModel)).thenReturn(persistenceModel);
            assertThrows(SearchItemNotFoundException.class,()->repository.actualizarOrden(domainModel));
            verify(interfaceRepository,times(1)).existsById(persistenceModel.getId_orden());
            verify(mapper,times(0)).toDomainModel(persistenceModel);
            verify(mapper,times(0)).toPersistenceModel(domainModel);
            verify(interfaceRepository,times(0)).save(persistenceModel);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void obtenerOrdenPorId() {
        try {
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(interfaceRepository.findById(persistenceModel.getId_orden())).thenReturn(Optional.of(persistenceModel));
            assertEquals(domainModel,repository.obtenerOrdenPorId(persistenceModel.getId_orden()));
            verify(mapper,times(1)).toDomainModel(persistenceModel);
            verify(interfaceRepository,times(1)).findById(persistenceModel.getId_orden());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void obtenerOrdenPorId_OrdenNoExiste() {
        try {
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(interfaceRepository.findById(persistenceModel.getId_orden())).thenReturn(Optional.empty());
            assertThrows(SearchItemNotFoundException.class,()->repository.obtenerOrdenPorId(persistenceModel.getId_orden()));
            verify(mapper,times(0)).toDomainModel(persistenceModel);
            verify(interfaceRepository,times(1)).findById(persistenceModel.getId_orden());
        } catch (Exception e) {
            fail(e);
        }
    }
}
package com.tienda.compras.adaptador.puerto.salida.persistencia.compra;

import com.tienda.compras.adaptador.modelo.persistencia.CompraEntity;
import com.tienda.compras.dominio.Compra;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;

class CompraRepositoryTest {
    private Compra domainModel=new Compra();
    private CompraEntity persistenceModel=new CompraEntity();

    private Usuario usuarioDomainModel=new Usuario();
    private UsuarioPersistenceModel usuarioPersistenceModel=new UsuarioPersistenceModel();

    private MapperRepositoryToDomainCompra mapper=mock(MapperRepositoryToDomainCompra.class);
    private CompraCrudRepository interfaceRepository=mock(CompraCrudRepository.class);
    private CompraRepository repository;

    @BeforeEach
    void setUp() {
        usuarioDomainModel.setId(1);
        usuarioPersistenceModel.setId(1);

        domainModel.setId_compra(1);
        domainModel.setFecha(LocalDateTime.now());
        domainModel.setUsuario(usuarioDomainModel);

        persistenceModel.setId_compra(1);
        persistenceModel.setFecha(LocalDateTime.now());
        persistenceModel.setUsuario(usuarioPersistenceModel);

        repository=new CompraRepository(mapper,interfaceRepository);
    }

    @Test
    void obtenerCompraPorId() {
        try {
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(interfaceRepository.findById(persistenceModel.getId_compra())).thenReturn(Optional.of(persistenceModel));
            assertEquals(domainModel,repository.obtenerCompraPorId(persistenceModel.getId_compra()));
            verify(mapper,times(1)).toDomainModel(persistenceModel);
            verify(interfaceRepository,times(1)).findById(persistenceModel.getId_compra());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void obtenerCompraPorId_CompraNoExiste() {
        try {
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(interfaceRepository.findById(persistenceModel.getId_compra())).thenReturn(Optional.empty());
            assertThrows(SearchItemNotFoundException.class,()->repository.obtenerCompraPorId(persistenceModel.getId_compra()));
            verify(mapper,times(0)).toDomainModel(persistenceModel);
            verify(interfaceRepository,times(1)).findById(persistenceModel.getId_compra());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void obtenerComprasPorIdUsuario() {
        try {
            when(interfaceRepository.findByUsuario_id(persistenceModel.getUsuario().getId())).thenReturn(List.of(persistenceModel));
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            assertEquals(List.of(domainModel),repository.obtenerComprasPorIdUsuario(persistenceModel.getUsuario().getId()));
            verify(mapper,times(1)).toDomainModel(persistenceModel);
            verify(interfaceRepository,times(1)).findByUsuario_id(persistenceModel.getUsuario().getId());
        }catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void registrarCompra() {
        try {
            when(interfaceRepository.existsById(persistenceModel.getId_compra())).thenReturn(false);
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(mapper.toPersistenceModel(domainModel)).thenReturn(persistenceModel);
            when(interfaceRepository.save(persistenceModel)).thenReturn(persistenceModel);
            assertEquals(domainModel,repository.registrarCompra(domainModel));
            verify(interfaceRepository,times(1)).existsById(persistenceModel.getId_compra());
            verify(mapper,times(1)).toDomainModel(persistenceModel);
            verify(mapper,times(1)).toPersistenceModel(domainModel);
            verify(interfaceRepository,times(1)).save(persistenceModel);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarCompra_CompraYaExiste() {
        try {
            when(interfaceRepository.existsById(persistenceModel.getId_compra())).thenReturn(true);
            when(mapper.toDomainModel(persistenceModel)).thenReturn(domainModel);
            when(mapper.toPersistenceModel(domainModel)).thenReturn(persistenceModel);
            when(interfaceRepository.save(persistenceModel)).thenReturn(persistenceModel);
            assertThrows(ItemAlreadyExistException.class,()->repository.registrarCompra(domainModel));
            verify(interfaceRepository,times(1)).existsById(persistenceModel.getId_compra());
            verify(mapper,times(0)).toDomainModel(persistenceModel);
            verify(mapper,times(0)).toPersistenceModel(domainModel);
            verify(interfaceRepository,times(0)).save(persistenceModel);
        } catch (Exception e) {
            fail(e);
        }
    }
}
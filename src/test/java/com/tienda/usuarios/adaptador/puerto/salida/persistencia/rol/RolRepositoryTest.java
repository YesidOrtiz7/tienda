package com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.persistencia.RolEntity;
import com.tienda.usuarios.dominio.Rol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RolRepositoryTest {
    private RolCrudRepository interfaceRepository;
    private MapperRepositoryToDomainRol mapper;
    private RolEntity rolPersistenceModel;
    private Rol rolDomainModel;

    private RolRepository repository;

    @BeforeEach
    void setUp() {
        rolPersistenceModel=new RolEntity();
        rolDomainModel=new Rol();

        rolPersistenceModel.setId(1);
        rolPersistenceModel.setNombre("Administrador");

        rolDomainModel.setId(1);
        rolDomainModel.setNombre("Administrador");

        /*rolPersistenceModel.setId(2);
        rolPersistenceModel.setNombre("Usuario");*/

        interfaceRepository= mock(RolCrudRepository.class);
        mapper=mock(MapperRepositoryToDomainRol.class);

        repository=new RolRepository(interfaceRepository,mapper);
    }

    @Test
    void obtenerRolPorId() {
        try {
            when(mapper.toDomainModel(rolPersistenceModel)).thenReturn(rolDomainModel);
            when(interfaceRepository.findById(1)).thenReturn(Optional.of(rolPersistenceModel));
            assertEquals(rolDomainModel,repository.obtenerRolPorId(1));
            verify(mapper,times(1)).toDomainModel(rolPersistenceModel);
            verify(interfaceRepository,times(1)).findById(1);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void obtenerRolPorId_RolNoExiste() {
        try {
            when(mapper.toDomainModel(rolPersistenceModel)).thenReturn(rolDomainModel);
            when(interfaceRepository.findById(1)).thenReturn(Optional.empty());
            assertThrows(InvalidInputException.class,()->repository.obtenerRolPorId(1));
            verify(mapper,times(0)).toDomainModel(rolPersistenceModel);
            verify(interfaceRepository,times(1)).findById(1);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void nuevoRol() {
        try {
            when(mapper.toPersistenceModel(rolDomainModel)).thenReturn(rolPersistenceModel);
            when(mapper.toDomainModel(rolPersistenceModel)).thenReturn(rolDomainModel);
            when(interfaceRepository.save(rolPersistenceModel)).thenReturn(rolPersistenceModel);
            when(interfaceRepository.existsById(rolPersistenceModel.getId())).thenReturn(false);
            assertEquals(rolDomainModel,repository.nuevoRol(rolDomainModel));
            verify(mapper,times(1)).toPersistenceModel(rolDomainModel);
            verify(mapper,times(1)).toDomainModel(rolPersistenceModel);
            verify(interfaceRepository,times(1)).save(rolPersistenceModel);
            verify(interfaceRepository,times(1)).existsById(rolPersistenceModel.getId());
        }catch (Exception e){
            fail(e);
        }
    }
    @Test
    void nuevoRol_RolExiste() {
        try {
            when(mapper.toPersistenceModel(rolDomainModel)).thenReturn(rolPersistenceModel);
            when(mapper.toDomainModel(rolPersistenceModel)).thenReturn(rolDomainModel);
            when(interfaceRepository.save(rolPersistenceModel)).thenReturn(rolPersistenceModel);
            when(interfaceRepository.existsById(rolPersistenceModel.getId())).thenReturn(true);
            assertThrows(ItemAlreadyExistException.class,()->repository.nuevoRol(rolDomainModel));
            verify(mapper,times(0)).toPersistenceModel(rolDomainModel);
            verify(mapper,times(0)).toDomainModel(rolPersistenceModel);
            verify(interfaceRepository,times(0)).save(rolPersistenceModel);
            verify(interfaceRepository,times(1)).existsById(rolPersistenceModel.getId());
        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    void eliminarRol() {
        try {
            when(interfaceRepository.existsById(rolPersistenceModel.getId())).thenReturn(true);
            assertTrue(repository.eliminarRol(rolPersistenceModel.getId()));
            verify(interfaceRepository,times(1)).existsById(rolPersistenceModel.getId());
            verify(interfaceRepository,times(1)).deleteById(rolPersistenceModel.getId());
        }catch (Exception e){
            fail(e);
        }
    }
    @Test
    void eliminarRol_RolNoExiste() {
        try {
            when(interfaceRepository.existsById(rolPersistenceModel.getId())).thenReturn(false);
            assertThrows(SearchItemNotFoundException.class, ()->repository.eliminarRol(rolPersistenceModel.getId()));
            verify(interfaceRepository,times(1)).existsById(rolPersistenceModel.getId());
            verify(interfaceRepository,times(0)).deleteById(rolPersistenceModel.getId());
        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    void actualizarRol() {
        try {
            when(interfaceRepository.existsById(rolPersistenceModel.getId())).thenReturn(true);
            when(interfaceRepository.save(rolPersistenceModel)).thenReturn(rolPersistenceModel);
            when(mapper.toDomainModel(rolPersistenceModel)).thenReturn(rolDomainModel);
            when(mapper.toPersistenceModel(rolDomainModel)).thenReturn(rolPersistenceModel);
            assertEquals(rolDomainModel,repository.actualizarRol(rolDomainModel));
            verify(mapper,times(1)).toDomainModel(rolPersistenceModel);
            verify(mapper,times(1)).toPersistenceModel(rolDomainModel);
            verify(interfaceRepository,times(1)).existsById(rolPersistenceModel.getId());
            verify(interfaceRepository,times(1)).save(rolPersistenceModel);
        }catch (Exception e){
            fail(e);
        }
    }
    @Test
    void actualizarRol_RolNoExiste() {
        try {
            when(interfaceRepository.existsById(rolPersistenceModel.getId())).thenReturn(false);
            when(interfaceRepository.save(rolPersistenceModel)).thenReturn(rolPersistenceModel);
            when(mapper.toDomainModel(rolPersistenceModel)).thenReturn(rolDomainModel);
            when(mapper.toPersistenceModel(rolDomainModel)).thenReturn(rolPersistenceModel);
            assertThrows(SearchItemNotFoundException.class,()->repository.actualizarRol(rolDomainModel));
            verify(mapper,times(0)).toDomainModel(rolPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(rolDomainModel);
            verify(interfaceRepository,times(1)).existsById(rolPersistenceModel.getId());
            verify(interfaceRepository,times(0)).save(rolPersistenceModel);
        }catch (Exception e){
            fail(e);
        }
    }

    /*@Test
    void obtenerRoles() {
    }*/
}
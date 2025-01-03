package com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
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
            //when(mapper.toPersistenceModel(rolDomainModel)).thenReturn(rolPersistenceModel);
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
        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    void eliminarRol() {
    }

    @Test
    void actualizarRol() {
    }

    @Test
    void obtenerRoles() {
    }
}
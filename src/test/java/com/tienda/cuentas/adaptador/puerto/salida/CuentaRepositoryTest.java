package com.tienda.cuentas.adaptador.puerto.salida;

import com.tienda.cuentas.adaptador.puerto.salida.modelos.CuentaEntity;
import com.tienda.cuentas.dominio.Cuenta;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.MapperRepositoryToDomainUsuario;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.UsuarioCrudRepository;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.UsuarioRepository;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CuentaRepositoryTest {
    private final Cuenta cuentaDomainModel=new Cuenta();
    private final CuentaEntity cuentaPersistenceModel=new CuentaEntity();

    private final Usuario usuarioDomainModel=new Usuario();
    private final UsuarioPersistenceModel usuarioPersistenceModel=new UsuarioPersistenceModel();

    private final CuentaCrudRepository interfaceRepository=mock(CuentaCrudRepository.class);
    private final UsuarioCrudRepository usuarioCrudRepository=mock(UsuarioCrudRepository.class);

    private final MapperRepositoryToDomainCuenta mapper=mock(MapperRepositoryToDomainCuenta.class);

    private CuentaRepository repository;

    @BeforeEach
    void setUp() {
        cuentaDomainModel.setId_usuario(1);
        cuentaDomainModel.setSaldo(1000);

        cuentaPersistenceModel.setId_usuario(1);
        cuentaPersistenceModel.setSaldo(1000);

        repository=new CuentaRepository(interfaceRepository,mapper,usuarioCrudRepository);
    }

    /*@Test
    void crearCuenta() {
        try {
            when(usuarioRepository.existById(cuentaPersistenceModel.getId_usuario())).thenReturn(true);
            when(interfaceRepository.existsById(cuentaPersistenceModel.getId_usuario())).thenReturn(false);
            when(interfaceRepository.save(cuentaPersistenceModel)).thenReturn(cuentaPersistenceModel);
            when(mapper.toDomainModel(cuentaPersistenceModel)).thenReturn(cuentaDomainModel);
            when(mapper.toPersistenceModel(cuentaDomainModel)).thenReturn(cuentaPersistenceModel);
            //obteniendo el usuario de la tabla de usuarios
            when(mapperUsuario.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            when(usuarioCrudRepository.findById(cuentaPersistenceModel.getId_usuario())).thenReturn(Optional.of(usuarioPersistenceModel));

            assertEquals(cuentaDomainModel,repository.crearCuenta(cuentaDomainModel));
            verify(usuarioRepository,times(1)).existById(cuentaPersistenceModel.getId_usuario());
            verify(interfaceRepository,times(1)).existsById(cuentaPersistenceModel.getId_usuario());
            verify(interfaceRepository,times(1)).save(cuentaPersistenceModel);
            verify(mapper,times(1)).toDomainModel(cuentaPersistenceModel);
            verify(mapper,times(1)).toPersistenceModel(cuentaDomainModel);

        }catch (Exception e){
            fail(e);
        }
    }*/
    @Test
    void crearCuenta_ElUsuarioYaTieneUnaCuenta() {
        try {
            when(interfaceRepository.existsById(cuentaPersistenceModel.getId_usuario())).thenReturn(true);
            when(interfaceRepository.save(cuentaPersistenceModel)).thenReturn(cuentaPersistenceModel);
            when(mapper.toDomainModel(cuentaPersistenceModel)).thenReturn(cuentaDomainModel);
            when(mapper.toPersistenceModel(cuentaDomainModel)).thenReturn(cuentaPersistenceModel);
            when(usuarioCrudRepository.existsById(cuentaPersistenceModel.getId_usuario())).thenReturn(true);

            //obteniendo el usuario de la tabla de usuarios
            when(usuarioCrudRepository.findById(cuentaPersistenceModel.getId_usuario())).thenReturn(Optional.of(usuarioPersistenceModel));

            assertThrows(InvalidInputException.class,()->repository.crearCuenta(cuentaDomainModel.getId_usuario()));
            verify(interfaceRepository,times(1)).existsById(cuentaPersistenceModel.getId_usuario());
            verify(interfaceRepository,times(0)).save(cuentaPersistenceModel);
            verify(mapper,times(0)).toDomainModel(cuentaPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(cuentaDomainModel);

        }catch (Exception e){
            fail(e);
        }
    }
    @Test
    void crearCuenta_ElUsuarioNoExiste() {
        try {
            when(interfaceRepository.existsById(cuentaPersistenceModel.getId_usuario())).thenReturn(false);
            when(interfaceRepository.save(cuentaPersistenceModel)).thenReturn(cuentaPersistenceModel);
            when(mapper.toDomainModel(cuentaPersistenceModel)).thenReturn(cuentaDomainModel);
            when(mapper.toPersistenceModel(cuentaDomainModel)).thenReturn(cuentaPersistenceModel);
            //obteniendo el usuario de la tabla de usuarios
            when(usuarioCrudRepository.findById(cuentaPersistenceModel.getId_usuario())).thenReturn(Optional.of(usuarioPersistenceModel));

            assertThrows(SearchItemNotFoundException.class,()->repository.crearCuenta(cuentaDomainModel.getId_usuario()));
            verify(interfaceRepository,times(0)).existsById(cuentaPersistenceModel.getId_usuario());
            verify(interfaceRepository,times(0)).save(cuentaPersistenceModel);
            verify(mapper,times(0)).toDomainModel(cuentaPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(cuentaDomainModel);

        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    void obtenerCuentaPorIdUsuario() {
        try{
            when(interfaceRepository.findById(cuentaPersistenceModel.getId_usuario())).thenReturn(Optional.of(cuentaPersistenceModel));
            when(mapper.toDomainModel(cuentaPersistenceModel)).thenReturn(cuentaDomainModel);
            assertEquals(cuentaDomainModel,repository.obtenerCuentaPorIdUsuario(cuentaPersistenceModel.getId_usuario()));
            verify(mapper,times(1)).toDomainModel(cuentaPersistenceModel);
            verify(interfaceRepository,times(1)).findById(cuentaPersistenceModel.getId_usuario());
        }catch (Exception e){
            fail(e);
        }
    }
    @Test
    void obtenerCuentaPorIdUsuario_UsuarioNoExiste() {
        try{
            when(interfaceRepository.findById(cuentaPersistenceModel.getId_usuario())).thenReturn(Optional.empty());
            when(mapper.toDomainModel(cuentaPersistenceModel)).thenReturn(cuentaDomainModel);
            assertThrows(SearchItemNotFoundException.class,()->repository.obtenerCuentaPorIdUsuario(cuentaPersistenceModel.getId_usuario()));
            verify(mapper,times(0)).toDomainModel(cuentaPersistenceModel);
            verify(interfaceRepository,times(1)).findById(cuentaPersistenceModel.getId_usuario());
        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    void actualizarSaldoCuenta() {
        try {
            when(interfaceRepository.existsById(cuentaPersistenceModel.getId_usuario())).thenReturn(true);
            when(mapper.toPersistenceModel(cuentaDomainModel)).thenReturn(cuentaPersistenceModel);
            when(mapper.toDomainModel(cuentaPersistenceModel)).thenReturn(cuentaDomainModel);
            when(interfaceRepository.save(cuentaPersistenceModel)).thenReturn(cuentaPersistenceModel);
            assertEquals(cuentaDomainModel,repository.actualizarSaldoCuenta(cuentaDomainModel));
            verify(interfaceRepository,times(1)).existsById(cuentaPersistenceModel.getId_usuario());
            verify(mapper,times(1)).toDomainModel(cuentaPersistenceModel);
            verify(mapper,times(1)).toPersistenceModel(cuentaDomainModel);
            verify(interfaceRepository,times(1)).save(cuentaPersistenceModel);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void actualizarSaldoCuenta_CuentaNoExiste() {
        try {
            when(interfaceRepository.existsById(cuentaPersistenceModel.getId_usuario())).thenReturn(false);
            when(mapper.toPersistenceModel(cuentaDomainModel)).thenReturn(cuentaPersistenceModel);
            when(mapper.toDomainModel(cuentaPersistenceModel)).thenReturn(cuentaDomainModel);
            when(interfaceRepository.save(cuentaPersistenceModel)).thenReturn(cuentaPersistenceModel);
            assertThrows(SearchItemNotFoundException.class,()->repository.actualizarSaldoCuenta(cuentaDomainModel));
            verify(interfaceRepository,times(1)).existsById(cuentaPersistenceModel.getId_usuario());
            verify(mapper,times(0)).toDomainModel(cuentaPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(cuentaDomainModel);
            verify(interfaceRepository,times(0)).save(cuentaPersistenceModel);
        } catch (Exception e) {
            fail(e);
        }
    }
}
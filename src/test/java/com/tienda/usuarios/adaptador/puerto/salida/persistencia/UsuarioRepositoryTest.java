package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.cuentas.aplicacion.puerto.salida.PuertoSalidaCuenta;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol.UsuarioRolRepository;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioRepositoryTest {
    private Usuario usuarioDomainModel=new Usuario();
    private UsuarioPersistenceModel usuarioPersistenceModel=new UsuarioPersistenceModel();

    private UsuarioCrudRepository crudRepository=mock(UsuarioCrudRepository.class);
    private MapperRepositoryToDomainUsuario mapper=mock(MapperRepositoryToDomainUsuario.class);
    private UsuarioRepository repository=new UsuarioRepository();
    private PasswordEncoder passwordEncoder=mock(PasswordEncoder.class);
    private PuertoSalidaCuenta cuentaRepository=mock(PuertoSalidaCuenta.class);
    private UsuarioRolRepository usuarioRolRepository=mock(UsuarioRolRepository.class);

    @BeforeEach
    void setUp() {
        usuarioDomainModel.setId(1);
        usuarioDomainModel.setDocumento("11111111");
        usuarioDomainModel.setNombres("Fabian");
        usuarioDomainModel.setApellidos("Ortiz");
        usuarioDomainModel.setCorreo("correo@Correo.com");
        usuarioDomainModel.setTelefono("1234567890");
        usuarioDomainModel.setContrasena("Asdf123+");
        usuarioDomainModel.setBloqueado(false);
        usuarioDomainModel.setEliminado(false);

        usuarioPersistenceModel.setId(1);
        usuarioPersistenceModel.setDocumento("11111111");
        usuarioPersistenceModel.setNombres("Fabian");
        usuarioPersistenceModel.setApellidos("Ortiz");
        usuarioPersistenceModel.setCorreo("correo@Correo.com");
        usuarioPersistenceModel.setTelefono("1234567890");
        usuarioPersistenceModel.setContrasena("Asdf123+");
        usuarioPersistenceModel.setBloqueado(false);
        usuarioPersistenceModel.setEliminado(false);

        repository.setRepository(crudRepository);
        repository.setMapper(mapper);
        repository.setPasswordEncoder(passwordEncoder);
        repository.setCuentaRepository(cuentaRepository);
        repository.setUsuarioRolRepository(usuarioRolRepository);
    }

    @Test
    void existById() {
        try {
            when(crudRepository.existsById(usuarioPersistenceModel.getId())).thenReturn(true);
            assertTrue(()->repository.existById(usuarioPersistenceModel.getId()));
            verify(crudRepository,times(1)).existsById(usuarioPersistenceModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void existById_usuarioNoExiste() {
        try {
            when(crudRepository.existsById(usuarioPersistenceModel.getId())).thenReturn(false);
            assertFalse(()->repository.existById(usuarioPersistenceModel.getId()));
            verify(crudRepository,times(1)).existsById(usuarioPersistenceModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void existByDocumento() {
        try {
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(true);
            assertTrue(()->repository.existByDocumento(usuarioPersistenceModel.getDocumento()));
            verify(crudRepository,times(1)).existsByDocumento(usuarioPersistenceModel.getDocumento());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void existByDocumento_usuarioNoExiste() {
        try {
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(false);
            assertFalse(()->repository.existByDocumento(usuarioPersistenceModel.getDocumento()));
            verify(crudRepository,times(1)).existsByDocumento(usuarioPersistenceModel.getDocumento());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void getById() {
        try {
            when(crudRepository.findById(usuarioPersistenceModel.getId())).thenReturn(Optional.ofNullable(usuarioPersistenceModel));
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            assertEquals(usuarioDomainModel,repository.getById(usuarioPersistenceModel.getId()));
            verify(crudRepository,times(1)).findById(usuarioPersistenceModel.getId());
            verify(mapper,times(1)).toDomainModel(usuarioPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void getById_usuarioNoExiste() {
        try {
            when(crudRepository.findById(usuarioPersistenceModel.getId())).thenReturn(Optional.empty());
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            assertThrows(SearchItemNotFoundException.class,()->repository.getById(usuarioPersistenceModel.getId()));
            verify(crudRepository,times(1)).findById(usuarioPersistenceModel.getId());
            verify(mapper,times(0)).toDomainModel(usuarioPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void getByDocument() {
        try {
            when(crudRepository.findByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(Optional.ofNullable(usuarioPersistenceModel));
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            assertEquals(usuarioDomainModel,repository.getByDocument(usuarioPersistenceModel.getDocumento()));
            verify(crudRepository,times(1)).findByDocumento(usuarioPersistenceModel.getDocumento());
            verify(mapper,times(1)).toDomainModel(usuarioPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void getByDocument_usuarioNoEncontrado() {
        try {
            when(crudRepository.findByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(Optional.empty());
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            assertThrows(SearchItemNotFoundException.class,()->repository.getByDocument(usuarioPersistenceModel.getDocumento()));
            verify(crudRepository,times(1)).findByDocumento(usuarioPersistenceModel.getDocumento());
            verify(mapper,times(0)).toDomainModel(usuarioPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void crearUsuario() {
        try {
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            when(crudRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(false);
            when(passwordEncoder.encode(usuarioPersistenceModel.getContrasena())).thenReturn(usuarioPersistenceModel.getContrasena());
            assertEquals(usuarioDomainModel, repository.crearUsuario(usuarioDomainModel,2));
            verify(mapper,times(1)).toPersistenceModel(usuarioDomainModel);
            verify(mapper,times(1)).toDomainModel(usuarioPersistenceModel);
            verify(crudRepository,times(1)).save(usuarioPersistenceModel);
            verify(crudRepository,times(1)).existsByDocumento(usuarioPersistenceModel.getDocumento());
            verify(passwordEncoder,times(1)).encode(usuarioPersistenceModel.getContrasena());
            verify(cuentaRepository,times(1)).crearCuenta(usuarioPersistenceModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void crearUsuario_documentoUsuarioYaExiste() {
        try {
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            when(crudRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(true);
            when(passwordEncoder.encode(usuarioPersistenceModel.getContrasena())).thenReturn(usuarioPersistenceModel.getContrasena());
            assertThrows(InvalidInputException.class, ()->repository.crearUsuario(usuarioDomainModel,2));
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
            verify(mapper,times(0)).toDomainModel(usuarioPersistenceModel);
            verify(crudRepository,times(0)).save(usuarioPersistenceModel);
            verify(crudRepository,times(1)).existsByDocumento(usuarioPersistenceModel.getDocumento());
            verify(passwordEncoder,times(0)).encode(usuarioPersistenceModel.getContrasena());
            verify(cuentaRepository,times(0)).crearCuenta(usuarioPersistenceModel.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearUsuario_contrasenaInvalida() {
        try {
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            when(crudRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(false);
            when(passwordEncoder.encode(usuarioPersistenceModel.getContrasena())).thenReturn(usuarioPersistenceModel.getContrasena());
            usuarioDomainModel.setContrasena("122334");
            assertThrows(InvalidInputException.class, ()->repository.crearUsuario(usuarioDomainModel,2));
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
            verify(mapper,times(0)).toDomainModel(usuarioPersistenceModel);
            verify(crudRepository,times(0)).save(usuarioPersistenceModel);
            verify(crudRepository,times(0)).existsByDocumento(usuarioPersistenceModel.getDocumento());
            verify(passwordEncoder,times(0)).encode(usuarioPersistenceModel.getContrasena());
            verify(cuentaRepository,times(0)).crearCuenta(usuarioPersistenceModel.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearUsuario_correoEnBlanco() {
        try {
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            when(crudRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(false);
            when(passwordEncoder.encode(usuarioPersistenceModel.getContrasena())).thenReturn(usuarioPersistenceModel.getContrasena());
            usuarioDomainModel.setCorreo("");
            assertThrows(InvalidInputException.class, ()->repository.crearUsuario(usuarioDomainModel,2));
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
            verify(mapper,times(0)).toDomainModel(usuarioPersistenceModel);
            verify(crudRepository,times(0)).save(usuarioPersistenceModel);
            verify(crudRepository,times(0)).existsByDocumento(usuarioPersistenceModel.getDocumento());
            verify(passwordEncoder,times(0)).encode(usuarioPersistenceModel.getContrasena());
            verify(cuentaRepository,times(0)).crearCuenta(usuarioPersistenceModel.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void crearUsuario_sinCuenta(){
        try {
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(false);
            when(crudRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            assertEquals(usuarioDomainModel,repository.crearUsuario_sinCuenta(usuarioDomainModel,2));
            verify(crudRepository,times(1)).existsByDocumento(usuarioPersistenceModel.getDocumento());
            verify(crudRepository,times(1)).save(usuarioPersistenceModel);
            verify(mapper,times(1)).toDomainModel(usuarioPersistenceModel);
            verify(mapper,times(1)).toPersistenceModel(usuarioDomainModel);
            verify(cuentaRepository,times(0)).crearCuenta(usuarioPersistenceModel.getId());

        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void crearUsuario_sinCuenta_documentoUsuarioYaExiste(){
        try {
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(true);
            when(crudRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            assertThrows(InvalidInputException.class,()->repository.crearUsuario_sinCuenta(usuarioDomainModel,2));
            verify(crudRepository,times(1)).existsByDocumento(usuarioPersistenceModel.getDocumento());
            verify(crudRepository,times(0)).save(usuarioPersistenceModel);
            verify(mapper,times(0)).toDomainModel(usuarioPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
            verify(cuentaRepository,times(0)).crearCuenta(usuarioPersistenceModel.getId());

        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void crearUsuario_sinCuenta_contrasenaInvalida(){
        try {
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(false);
            when(crudRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            usuarioDomainModel.setContrasena("123456");
            assertThrows(InvalidInputException.class,()->repository.crearUsuario_sinCuenta(usuarioDomainModel,2));
            verify(crudRepository,times(0)).existsByDocumento(usuarioPersistenceModel.getDocumento());
            verify(crudRepository,times(0)).save(usuarioPersistenceModel);
            verify(mapper,times(0)).toDomainModel(usuarioPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
            verify(cuentaRepository,times(0)).crearCuenta(usuarioPersistenceModel.getId());

        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void crearUsuario_sinCuenta_correoEnBlanco(){
        try {
            when(crudRepository.existsByDocumento(usuarioPersistenceModel.getDocumento())).thenReturn(false);
            when(crudRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            usuarioDomainModel.setCorreo("");
            assertThrows(InvalidInputException.class,()->repository.crearUsuario_sinCuenta(usuarioDomainModel,2));
            verify(crudRepository,times(0)).existsByDocumento(usuarioPersistenceModel.getDocumento());
            verify(crudRepository,times(0)).save(usuarioPersistenceModel);
            verify(mapper,times(0)).toDomainModel(usuarioPersistenceModel);
            verify(mapper,times(0)).toPersistenceModel(usuarioDomainModel);
            verify(cuentaRepository,times(0)).crearCuenta(usuarioPersistenceModel.getId());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void actualizarUsuario() {
    }

    @Test
    void deleteById() {
        try {
            when(crudRepository.existsById(usuarioPersistenceModel.getId())).thenReturn(true);
            assertTrue(repository.deleteById(usuarioPersistenceModel.getId()));
            verify(crudRepository,times(1)).deleteById(usuarioPersistenceModel.getId());
            verify(crudRepository,times(1)).existsById(usuarioPersistenceModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void deleteById_usuarioNoExiste() {
        try {
            when(crudRepository.existsById(usuarioPersistenceModel.getId())).thenReturn(false);
            assertFalse(repository.deleteById(usuarioPersistenceModel.getId()));
            verify(crudRepository,times(0)).deleteById(usuarioPersistenceModel.getId());
            verify(crudRepository,times(1)).existsById(usuarioPersistenceModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
}
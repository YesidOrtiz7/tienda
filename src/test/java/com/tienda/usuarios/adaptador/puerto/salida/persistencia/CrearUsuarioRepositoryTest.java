package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.cuentas.aplicacion.puerto.salida.PuertoSalidaCuenta;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class CrearUsuarioRepositoryTest {
    private Usuario usuarioDomainModel;
    private UsuarioPersistenceModel usuarioPersistenceModel;
    private  MapperRepositoryToDomainUsuario mapper;
    private CrearUsuarioRepository repository=new CrearUsuarioRepository();
    private UsuarioCrudRepository interfaceRepository;
    private PuertoSalidaCuenta repositoryCuenta=Mockito.mock(PuertoSalidaCuenta.class);
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp(){
        usuarioDomainModel=new Usuario();

        usuarioDomainModel.setId(1);
        usuarioDomainModel.setDocumento("11111111");
        usuarioDomainModel.setNombres("Fabian");
        usuarioDomainModel.setApellidos("Ortiz");
        usuarioDomainModel.setCorreo("correo@correo.com");
        usuarioDomainModel.setContrasena("Asdf123+");
        usuarioDomainModel.setBloqueado(false);
        usuarioDomainModel.setEliminado(false);

        usuarioPersistenceModel=new UsuarioPersistenceModel();
        usuarioPersistenceModel.setId(1);
        usuarioPersistenceModel.setDocumento("1111");
        usuarioPersistenceModel.setNombres("Juan Joaquin");
        usuarioPersistenceModel.setApellidos("Jimenez");
        usuarioPersistenceModel.setCorreo("correo@correo.com");
        usuarioPersistenceModel.setContrasena("Asdf123+");
        usuarioPersistenceModel.setBloqueado(false);
        usuarioPersistenceModel.setEliminado(false);
        mapper= Mockito.mock(MapperRepositoryToDomainUsuario.class);
        passwordEncoder=Mockito.mock(PasswordEncoder.class);
        interfaceRepository=Mockito.mock(UsuarioCrudRepository.class);
        repository.setMapper(mapper);
        repository.setRepository(interfaceRepository);
        repository.setPasswordEncoder(passwordEncoder);
        repository.setCuentaRepository(repositoryCuenta);
    }

    @Test
    void crearUsuario() {
        try {
            Mockito.when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            Mockito.when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            Mockito.when(interfaceRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            Mockito.when(passwordEncoder.encode(usuarioPersistenceModel.getContrasena())).thenReturn(usuarioPersistenceModel.getContrasena());
            Assertions.assertEquals(usuarioDomainModel, repository.crearUsuario(usuarioDomainModel));
            Mockito.verify(mapper,Mockito.times(1)).toPersistenceModel(usuarioDomainModel);
            Mockito.verify(mapper,Mockito.times(1)).toDomainModel(usuarioPersistenceModel);
            Mockito.verify(interfaceRepository,Mockito.times(1)).save(usuarioPersistenceModel);
            Mockito.verify(passwordEncoder,Mockito.times(1)).encode(usuarioPersistenceModel.getContrasena());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    /*@Test
    void crearUsuario_ContrasenaInvalida() {
        try {
            Mockito.when(mapper.toDomainModel(usuarioPersistenceModel)).thenReturn(usuarioDomainModel);
            Mockito.when(mapper.toPersistenceModel(usuarioDomainModel)).thenReturn(usuarioPersistenceModel);
            Mockito.when(interfaceRepository.save(usuarioPersistenceModel)).thenReturn(usuarioPersistenceModel);
            usuarioDomainModel.setContrasena("1111111111");
            Assertions.assertThrows(InvalidInputException.class, ()->repository.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setContrasena("            ");
            Assertions.assertThrows(InvalidInputException.class, ()->repository.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setContrasena("asdfasd");
            Assertions.assertThrows(InvalidInputException.class, ()->repository.crearUsuario(usuarioDomainModel));
            Mockito.verify(mapper,Mockito.times(0)).toPersistenceModel(usuarioDomainModel);
            Mockito.verify(mapper,Mockito.times(0)).toDomainModel(usuarioPersistenceModel);
            Mockito.verify(interfaceRepository,Mockito.times(0)).save(usuarioPersistenceModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }*/
}
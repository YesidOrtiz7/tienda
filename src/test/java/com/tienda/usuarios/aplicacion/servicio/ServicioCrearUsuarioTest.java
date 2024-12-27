package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoCrearUsuario;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ServicioCrearUsuarioTest {
    private Usuario domainModel;
    private PuertoCrearUsuario repository;
    private ServicioCrearUsuario service=new ServicioCrearUsuario();

    @BeforeEach
    void setUp() {
        repository=Mockito.mock(PuertoCrearUsuario.class);
        //domainModel=new Usuario(1,"11111111","Fabian","","Ortiz","","Asdf123+",true);
        domainModel=new Usuario();
        domainModel.setId(1);
        domainModel.setDocumento("11111111");
        domainModel.setNombres("Fabian");
        domainModel.setApellidos("Ortiz");
        domainModel.setCorreo("correo@Correo.com");
        domainModel.setTelefono("123456");
        domainModel.setContrasena("Asdf123+");
        domainModel.setBloqueado(true);
        domainModel.setSaldoEnCuenta(0);
        service.setRepository(repository);
    }

    @Test
    void crearUsuario() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);
            String totp=service.crearUsuario(domainModel);
            System.out.println(totp);
            Assertions.assertTrue(totp.startsWith("otpauth://totp/"));
            Mockito.verify(repository,Mockito.times(1)).crearUsuario(domainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearUsuario_NombresInvalidos() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);
            domainModel.setNombres("1234asdf");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setNombres("      ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setNombres("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));

            Mockito.verify(repository,Mockito.times(0)).crearUsuario(domainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void crearUsuario_ApellidosInvalidos() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);

            domainModel.setApellidos("1234asdf");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setApellidos("      ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setApellidos("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));

            Mockito.verify(repository,Mockito.times(0)).crearUsuario(domainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearUsuario_CorreoInvalido() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);
            domainModel.setCorreo("1234asdf");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setCorreo("1234asdf@ju");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setCorreo("1234asdf@asdf.123");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setCorreo("      ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setCorreo("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));

            Mockito.verify(repository,Mockito.times(0)).crearUsuario(domainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearUsuario_TelefonoInvalido() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);

            domainModel.setTelefono("ABC123");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setTelefono("/#");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setTelefono("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setTelefono("    ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setTelefono("1 2 3.4,5");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setTelefono("12.345, 678   ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setTelefono("+52 123-456-7890");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));

            Mockito.verify(repository,Mockito.times(0)).crearUsuario(domainModel);

            domainModel.setTelefono("+52 1234567890");
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void crearUsuario_DocumentoInvalido() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);

            domainModel.setDocumento("ABC123");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setDocumento("/#");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setDocumento("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setDocumento("    ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));

            Mockito.verify(repository,Mockito.times(0)).crearUsuario(domainModel);

            domainModel.setDocumento("1 2 3.4,5");
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
            domainModel.setDocumento("12.345, 678   ");
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearUsuario_ContrasenaInvalida() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);

            //contraseña sin caracteres especiales
            domainModel.setContrasena("Abcd123");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            //contraseña sin letras minusculas
            domainModel.setContrasena("ABCD123+");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            //contraseña con espacios
            domainModel.setContrasena("Abcd   123+");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            //contraseña longitud insuficiente
            domainModel.setContrasena("Ab123+");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));

            Mockito.verify(repository,Mockito.times(0)).crearUsuario(domainModel);

            domainModel.setContrasena("Asdf123+");
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
            domainModel.setContrasena("Asdf123@");;
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
            domainModel.setContrasena("Asdf123-");;
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
            domainModel.setContrasena("Asdf123*");;
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
            domainModel.setContrasena("Asdf123/");;
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
}
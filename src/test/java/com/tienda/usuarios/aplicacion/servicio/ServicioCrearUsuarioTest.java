package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoCrearUsuario;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

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
        domainModel.setPrimerNombre("Fabian");
        domainModel.setSegundoNombre("");
        domainModel.setPrimerApellido("Ortiz");
        domainModel.setSegundoApellido("");
        domainModel.setContrasena("Asdf123+");
        domainModel.setHabilitado(true);
        domainModel.setSaldoEnCuenta(0);
        service.setRepository(repository);
    }

    @Test
    void crearUsuario() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);
            Assertions.assertEquals(domainModel,service.crearUsuario(domainModel));
            Mockito.verify(repository,Mockito.times(1)).crearUsuario(domainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearUsuario_NombresInvalidos() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);
            domainModel.setPrimerNombre("1234asdf");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setSegundoNombre("1234asdf");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setPrimerNombre("      ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setPrimerNombre("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));

            Mockito.verify(repository,Mockito.times(0)).crearUsuario(domainModel);
            domainModel.setPrimerNombre("asf");

            domainModel.setSegundoNombre("      ");
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
            domainModel.setSegundoNombre("");
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void crearUsuario_ApellidosInvalidos() {
        try {
            Mockito.when(repository.crearUsuario(domainModel)).thenReturn(domainModel);
            domainModel.setPrimerApellido("1234asdf");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setSegundoApellido("1234asdf");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setPrimerApellido("      ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));
            domainModel.setPrimerApellido("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearUsuario(domainModel));

            Mockito.verify(repository,Mockito.times(0)).crearUsuario(domainModel);
            domainModel.setPrimerApellido("asf");

            domainModel.setSegundoApellido("      ");
            Assertions.assertDoesNotThrow(()->service.crearUsuario(domainModel));
            domainModel.setSegundoApellido("");
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
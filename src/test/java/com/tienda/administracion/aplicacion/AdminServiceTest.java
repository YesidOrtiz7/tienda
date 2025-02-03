package com.tienda.administracion.aplicacion;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import com.tienda.usuarios.aplicacion.puerto.salida.UsuariosPagSort_portOut;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {
    private Usuario usuarioDomainModel=new Usuario();
    private PuertoSalidaUsuario repository=mock(PuertoSalidaUsuario.class);
    private UsuariosPagSort_portOut pagSortPortOut=mock(UsuariosPagSort_portOut.class);
    private AdminService service;

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

        service=new AdminService(repository,pagSortPortOut);
    }

    @Test
    void existePorId() {
        try {
            when(repository.existById(usuarioDomainModel.getId())).thenReturn(true);
            assertTrue(service.existePorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).existById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void existePorId_usuarioNoExiste() {
        try {
            when(repository.existById(usuarioDomainModel.getId())).thenReturn(false);
            assertFalse(service.existePorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).existById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void obtenerAdmins() {
    }

    @Test
    void obtenerAdminPorId() {
        try{
            when(repository.getById(usuarioDomainModel.getId())).thenReturn(usuarioDomainModel);
            assertEquals(usuarioDomainModel,service.obtenerUsuarioPorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).getById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void obtenerAdminPorId_usuarioNoExiste4() {
        try{
            when(repository.getById(usuarioDomainModel.getId())).thenThrow(SearchItemNotFoundException.class);
            assertThrows(SearchItemNotFoundException.class,()->service.obtenerUsuarioPorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).getById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void obtenerPorDocumento() {
        try {
            when(repository.getByDocument(usuarioDomainModel.getDocumento())).thenReturn(usuarioDomainModel);
            assertEquals(usuarioDomainModel,service.obtenerPorDocumento(usuarioDomainModel.getDocumento()));
            verify(repository,times(1)).getByDocument(usuarioDomainModel.getDocumento());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void obtenerPorDocumento_usuarioNoExiste() {
        try {
            when(repository.getByDocument(usuarioDomainModel.getDocumento())).thenThrow(SearchItemNotFoundException.class);
            assertThrows(SearchItemNotFoundException.class,()->service.obtenerPorDocumento(usuarioDomainModel.getDocumento()));
            verify(repository,times(1)).getByDocument(usuarioDomainModel.getDocumento());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void registrarAdmin() {
        try {
            when(repository.crearUsuario_sinCuenta(usuarioDomainModel,1)).thenReturn(usuarioDomainModel);
            String totp=service.registrarAdmin(usuarioDomainModel);
            Assertions.assertTrue(totp.startsWith("otpauth://totp/"));
            verify(repository,times(1)).crearUsuario_sinCuenta(usuarioDomainModel,1);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarAdmin_NombresInvalidos() {
        try {
            when(repository.crearUsuario_sinCuenta(usuarioDomainModel,1)).thenReturn(usuarioDomainModel);
            usuarioDomainModel.setNombres("1234asdf");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setNombres("      ");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setNombres("");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario_sinCuenta(usuarioDomainModel,1);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarAdmin_apellidosInvalidos() {
        try {
            when(repository.crearUsuario_sinCuenta(usuarioDomainModel,1)).thenReturn(usuarioDomainModel);
            usuarioDomainModel.setApellidos("1234asdf");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setApellidos("      ");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setApellidos("");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario_sinCuenta(usuarioDomainModel,1);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarAdmin_correoInvalido() {
        try {
            when(repository.crearUsuario_sinCuenta(usuarioDomainModel,1)).thenReturn(usuarioDomainModel);
            usuarioDomainModel.setCorreo("1234asdf");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setCorreo("1234asdf@ju");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setCorreo("1234asdf@asdf.123");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setCorreo("1234asdf@asdf.2com");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setCorreo("           ");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setCorreo("");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario_sinCuenta(usuarioDomainModel,1);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarAdmin_telefonoInvalido() {
        try {
            when(repository.crearUsuario_sinCuenta(usuarioDomainModel,1)).thenReturn(usuarioDomainModel);
            usuarioDomainModel.setTelefono("ABC123");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setTelefono("/#");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setTelefono("");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setTelefono("    ");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setTelefono("1 2 3.4,5");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setTelefono("12.345, 678   ");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setTelefono("+52 123-456-7890");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario_sinCuenta(usuarioDomainModel,1);

            usuarioDomainModel.setTelefono("+52 1234567890");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setTelefono("1234567890");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarAdmin_documentoInvalido() {
        try {
            when(repository.crearUsuario_sinCuenta(usuarioDomainModel,1)).thenReturn(usuarioDomainModel);

            usuarioDomainModel.setDocumento("ABC123");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setDocumento("/#");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setDocumento("");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setDocumento("    ");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario_sinCuenta(usuarioDomainModel,1);

            usuarioDomainModel.setDocumento("1 2 3.4,5");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setDocumento("12.345, 678   ");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarAdmin_contrasenaInvalida() {
        try {
            when(repository.crearUsuario_sinCuenta(usuarioDomainModel,1)).thenReturn(usuarioDomainModel);

            //contraseña sin caracteres especiales
            usuarioDomainModel.setContrasena("Abcd123");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            //contraseña sin letras minusculas
            usuarioDomainModel.setContrasena("ABCD123+");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            //contraseña con espacios
            usuarioDomainModel.setContrasena("Abcd   123+");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));
            //contraseña longitud insuficiente
            usuarioDomainModel.setContrasena("Ab123+");
            assertThrows(InvalidInputException.class,()->service.registrarAdmin(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario_sinCuenta(usuarioDomainModel,1);

            usuarioDomainModel.setContrasena("Asdf123+");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setContrasena("Asdf123@");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setContrasena("Asdf123-");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setContrasena("Asdf123*");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
            usuarioDomainModel.setContrasena("Asdf123/");
            assertDoesNotThrow(()->service.registrarAdmin(usuarioDomainModel));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void actualizarAdmin() {
    }

    @Test
    void eliminarAdminPorId() {
        try {
            when(repository.deleteById(usuarioDomainModel.getId())).thenReturn(true);
            assertTrue(service.eliminarAdminPorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).deleteById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void eliminarAdminPorId_usuarioNoExiste() {
        try {
            when(repository.deleteById(usuarioDomainModel.getId())).thenThrow(SearchItemNotFoundException.class);
            assertThrows(SearchItemNotFoundException.class,()->service.eliminarAdminPorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).deleteById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
}
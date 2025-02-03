package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.puerto.salida.InhabilitarUsuarioQuery_portOut;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import com.tienda.usuarios.aplicacion.puerto.salida.UsuariosPagSort_portOut;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioUsuarioTest {
    private Usuario usuarioDomainModel;
    private final PuertoSalidaUsuario repository=mock(PuertoSalidaUsuario.class);
    private final UsuariosPagSort_portOut pagSortPortOut=mock(UsuariosPagSort_portOut.class);
    private final InhabilitarUsuarioQuery_portOut inhabilitarUsuarioRepository=mock(InhabilitarUsuarioQuery_portOut.class);
    private ServicioUsuario service;

    @BeforeEach
    void setUp() {
        usuarioDomainModel=new Usuario();
        usuarioDomainModel.setId(1);
        usuarioDomainModel.setDocumento("11111111");
        usuarioDomainModel.setNombres("Fabian");
        usuarioDomainModel.setApellidos("Ortiz");
        usuarioDomainModel.setCorreo("correo@Correo.com");
        usuarioDomainModel.setTelefono("1234567890");
        usuarioDomainModel.setContrasena("Asdf123+");
        usuarioDomainModel.setBloqueado(false);
        usuarioDomainModel.setEliminado(false);

        service=new ServicioUsuario(repository,inhabilitarUsuarioRepository,pagSortPortOut);
    }

    @Test
    void crearUsuario() {
        try {
            when(repository.crearUsuario(usuarioDomainModel,2)).thenReturn(usuarioDomainModel);
            String totp=service.crearUsuario(usuarioDomainModel);
            System.out.println(totp);
            Assertions.assertTrue(totp.startsWith("otpauth://totp/"));
            Mockito.verify(repository,Mockito.times(1)).crearUsuario(usuarioDomainModel,2);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void crearUsuario_NombresInvalidos() {
        try {
            when(repository.crearUsuario(usuarioDomainModel,2)).thenReturn(usuarioDomainModel);
            usuarioDomainModel.setNombres("1234asdf");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setNombres("      ");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setNombres("");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario(usuarioDomainModel,2);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void crearUsuario_ApellidosInvalidos() {
        try {
            when(repository.crearUsuario(usuarioDomainModel,2)).thenReturn(usuarioDomainModel);

            usuarioDomainModel.setApellidos("1234asdf");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setApellidos("      ");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setApellidos("");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario(usuarioDomainModel,2);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void crearUsuario_CorreoInvalido() {
        try {
            when(repository.crearUsuario(usuarioDomainModel,2)).thenReturn(usuarioDomainModel);
            usuarioDomainModel.setCorreo("1234asdf");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setCorreo("1234asdf@ju");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setCorreo("1234asdf@asdf.123");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setCorreo("      ");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setCorreo("");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario(usuarioDomainModel,2);
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void crearUsuario_TelefonoInvalido() {
        try {
            when(repository.crearUsuario(usuarioDomainModel,2)).thenReturn(usuarioDomainModel);

            usuarioDomainModel.setTelefono("ABC123");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setTelefono("/#");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setTelefono("");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setTelefono("    ");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setTelefono("1 2 3.4,5");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setTelefono("12.345, 678   ");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setTelefono("+52 123-456-7890");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario(usuarioDomainModel,2);

            usuarioDomainModel.setTelefono("+52 1234567890");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setTelefono("1234567890");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void crearUsuario_DocumentoInvalido() {
        try {
            when(repository.crearUsuario(usuarioDomainModel,2)).thenReturn(usuarioDomainModel);

            usuarioDomainModel.setDocumento("ABC123");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setDocumento("/#");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setDocumento("");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setDocumento("    ");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario(usuarioDomainModel,2);

            usuarioDomainModel.setDocumento("1 2 3.4,5");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setDocumento("12.345, 678   ");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void crearUsuario_ContrasenaInvalida() {
        try {
            when(repository.crearUsuario(usuarioDomainModel,2)).thenReturn(usuarioDomainModel);

            //contraseña sin caracteres especiales
            usuarioDomainModel.setContrasena("Abcd123");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            //contraseña sin letras minusculas
            usuarioDomainModel.setContrasena("ABCD123+");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            //contraseña con espacios
            usuarioDomainModel.setContrasena("Abcd   123+");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));
            //contraseña longitud insuficiente
            usuarioDomainModel.setContrasena("Ab123+");
            assertThrows(InvalidInputException.class,()->service.crearUsuario(usuarioDomainModel));

            verify(repository, times(0)).crearUsuario(usuarioDomainModel,2);

            usuarioDomainModel.setContrasena("Asdf123+");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setContrasena("Asdf123@");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setContrasena("Asdf123-");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setContrasena("Asdf123*");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
            usuarioDomainModel.setContrasena("Asdf123/");
            assertDoesNotThrow(()->service.crearUsuario(usuarioDomainModel));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void bloquear_bloquearUsuario() {
        try {
            when(repository.existById(usuarioDomainModel.getId())).thenReturn(true);
            service.bloquear(usuarioDomainModel.getId(),false);
            verify(repository,times(1)).existById(usuarioDomainModel.getId());
            verify(inhabilitarUsuarioRepository,times(1)).bloquear(usuarioDomainModel.getId());
            verify(inhabilitarUsuarioRepository,times(0)).desbloquear(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void bloquear_desbloquearUsuario() {
        try {
            when(repository.existById(usuarioDomainModel.getId())).thenReturn(true);
            service.bloquear(usuarioDomainModel.getId(),true);
            verify(repository,times(1)).existById(usuarioDomainModel.getId());
            verify(inhabilitarUsuarioRepository,times(0)).bloquear(usuarioDomainModel.getId());
            verify(inhabilitarUsuarioRepository,times(1)).desbloquear(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void bloquear_usuarioNoExiste() {
        try {
            when(repository.existById(usuarioDomainModel.getId())).thenReturn(false);
            assertThrows(
                    SearchItemNotFoundException.class,
                    ()->service.bloquear(usuarioDomainModel.getId(),true)
            );
            verify(repository,times(1)).existById(usuarioDomainModel.getId());
            verify(inhabilitarUsuarioRepository,times(0)).bloquear(usuarioDomainModel.getId());
            verify(inhabilitarUsuarioRepository,times(0)).desbloquear(usuarioDomainModel.getId());
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
    void obtenerPorId() {
        try {
            when(repository.getById(usuarioDomainModel.getId())).thenReturn(usuarioDomainModel);
            assertEquals(usuarioDomainModel,service.obtenerPorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).getById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void obtenerPorId_usuarioNoExiste() {
        try {
            when(repository.getById(usuarioDomainModel.getId())).thenThrow(SearchItemNotFoundException.class);
            assertThrows(SearchItemNotFoundException.class,()->service.obtenerPorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).getById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
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
    void existByDocumento(){
        try {
            when(repository.existByDocumento(usuarioDomainModel.getDocumento())).thenReturn(true);
            assertTrue(service.existByDocumento(usuarioDomainModel.getDocumento()));
            verify(repository,times(1)).existByDocumento(usuarioDomainModel.getDocumento());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void existByDocumento_usuarioNoExiste(){
        try {
            when(repository.existByDocumento(usuarioDomainModel.getDocumento())).thenReturn(false);
            assertFalse(service.existByDocumento(usuarioDomainModel.getDocumento()));
            verify(repository,times(1)).existByDocumento(usuarioDomainModel.getDocumento());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void eliminarPorId() {
        try {
            when(repository.deleteById(usuarioDomainModel.getId())).thenReturn(true);
            assertTrue(service.eliminarPorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).deleteById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void eliminarPorId_usuarioNoExiste() {
        try {
            when(repository.deleteById(usuarioDomainModel.getId())).thenThrow(SearchItemNotFoundException.class);
            assertThrows(SearchItemNotFoundException.class,()->service.eliminarPorId(usuarioDomainModel.getId()));
            verify(repository,times(1)).deleteById(usuarioDomainModel.getId());
        } catch (Exception e) {
            fail(e);
        }
    }
}
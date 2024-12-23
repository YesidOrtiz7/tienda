package com.tienda.publicaciones.aplicacion;

import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.publicaciones.aplicacion.puerto.salida.PublicacionPortOut;
import com.tienda.publicaciones.dominio.Publicacion;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoObtenerUsuarioPorDocumento;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoValidarUsuario;
import com.tienda.usuarios.aplicacion.servicio.ServicioValidarUsuario;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;


class PublicacionServiceTest {
    private PublicacionPortOut repository;
    private PublicacionService service=new PublicacionService();
    private Publicacion publicacion;
    private ArrayList<Publicacion> publicaciones=new ArrayList<>();
    private Usuario usuario=new Usuario();
    private CasoUsoValidarUsuario validarUsuario;
    private CasoUsoObtenerUsuarioPorDocumento obtenerUsuarioPorDocumentoMock;

    @BeforeEach
    void setUp() {
        repository= Mockito.mock(PublicacionPortOut.class);
        validarUsuario=Mockito.mock(CasoUsoValidarUsuario.class);
        obtenerUsuarioPorDocumentoMock=Mockito.mock(CasoUsoObtenerUsuarioPorDocumento.class);
        usuario.setId(1);
        usuario.setDocumento("11111111");
        usuario.setPrimerNombre("Fabian");
        usuario.setSegundoNombre("");
        usuario.setPrimerApellido("Ortiz");
        usuario.setSegundoApellido("");
        usuario.setContrasena("Asdf123+");
        usuario.setHabilitado(true);
        usuario.setSaldoEnCuenta(0);
        service.setPortOut(repository);
        publicacion=new Publicacion(1,"Tomate cherry","Tomate cherry de alta calidad",10.5,new Categoria(1,"frutas"),5, LocalDateTime.now());
        publicacion.setUsuario(usuario);
        service.setValidacionUsuario(validarUsuario);
        service.setObtenerUsuarioPorDocumento(obtenerUsuarioPorDocumentoMock);
        publicaciones.add(publicacion);
    }

    @Test
    void obtenerPublicaciones() {
        Mockito.when(repository.obtenerPublicaciones()).thenReturn(publicaciones);
        service.obtenerPublicaciones();
        Mockito.verify(repository,Mockito.times(1)).obtenerPublicaciones();
    }

    @Test
    void obtenerPublicacion() {
        try {
            Mockito.when(repository.obtenerPublicacion(publicacion.getId())).thenReturn(publicacion);
            service.obtenerPublicacion(publicacion.getId());
            Mockito.verify(repository,Mockito.times(1)).obtenerPublicacion(publicacion.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void obtenerPublicacion_PublicacionNoExiste() {
        try {
            Mockito.when(repository.obtenerPublicacion(publicacion.getId())).thenThrow(SearchItemNotFoundException.class);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->service.obtenerPublicacion(publicacion.getId()));
            Mockito.verify(repository,Mockito.times(1)).obtenerPublicacion(publicacion.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void crearPublicacion() {
        try {
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(repository.crearPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);
            Assertions.assertEquals(publicacion,service.crearPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(1)).crearPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(1)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearPublicacion_DescripcionEnBlanco() {
        try {
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(repository.crearPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            publicacion.setDescripcion("           ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            publicacion.setDescripcion("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).crearPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(0)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearPublicacion_ValorDelProductoInvalida() {
        try {
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(repository.crearPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            publicacion.setPrecio(-1);
            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).crearPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(0)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearPublicacion_CantidadDeUnidadesInvalida() {
        try {

            Mockito.when(repository.crearPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            publicacion.setCantidadDisponible(-1);
            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            publicacion.setCantidadDisponible(0);
            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).crearPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(0)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearPublicacion_UsuarioNoExiste() {
        try {

            Mockito.when(repository.crearPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(false);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).crearPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(1)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearPublicacion_TituloInvalido() {
        try {
            Mockito.when(repository.crearPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            publicacion.setTituloPublicacion("            ");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            publicacion.setTituloPublicacion("1111111");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            publicacion.setTituloPublicacion("");
            Assertions.assertThrows(InvalidInputException.class,()->service.crearPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).crearPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(0)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void actualizarPublicacion() {
        try {
            Mockito.when(repository.actualizarPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            Assertions.assertEquals(publicacion,service.actualizarPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(1)).actualizarPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(1)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarPublicacion_CantidadDeUnidadesInvalida() {
        try {
            Mockito.when(repository.actualizarPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            publicacion.setCantidadDisponible(-1);
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarPublicacion(publicacion));

            Mockito.verify(repository,Mockito.times(0)).actualizarPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(0)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarPublicacion_ValorDelProductoInvalida() {
        try {
            Mockito.when(repository.actualizarPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            publicacion.setPrecio(-1);
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).actualizarPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(0)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarPublicacion_DescripcionEnBlanco() {
        try {
            Mockito.when(repository.actualizarPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            publicacion.setDescripcion("     ");
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarPublicacion(publicacion));
            publicacion.setDescripcion("");
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).actualizarPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(0)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarPublicacion_PublicacionNoExiste() {
        try {
            /*en esta prueba se pasan todas las verificaciones y se deposita en el repositorio la responsabilidad
            * de determinar si la publicacion que se esta tratando de actualizar existe*/
            Mockito.when(repository.actualizarPublicacion(publicacion)).thenThrow(SearchItemNotFoundException.class);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            Assertions.assertThrows(SearchItemNotFoundException.class,()->service.actualizarPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(1)).actualizarPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(1)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarPublicacion_TituloInvalido() {
        try {
            Mockito.when(repository.actualizarPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(true);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            publicacion.setTituloPublicacion("        ");
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarPublicacion(publicacion));
            publicacion.setTituloPublicacion("1111111");
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarPublicacion(publicacion));
            publicacion.setTituloPublicacion("");
            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).actualizarPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(0)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarPublicacion_UsuarioNoExiste() {
        try {
            Mockito.when(repository.actualizarPublicacion(publicacion)).thenReturn(publicacion);
            Mockito.when(validarUsuario.validarUsuarioExiste(usuario.getId())).thenReturn(false);
            Mockito.when(obtenerUsuarioPorDocumentoMock.obtenerPorDocumento(usuario.getDocumento())).thenReturn(usuario);

            Assertions.assertThrows(InvalidInputException.class,()->service.actualizarPublicacion(publicacion));
            Mockito.verify(repository,Mockito.times(0)).actualizarPublicacion(publicacion);
            Mockito.verify(validarUsuario,Mockito.times(1)).validarUsuarioExiste(usuario.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void eliminarPublicacion() {
        try {
            Mockito.when(repository.eliminarPublicacion(publicacion.getId())).thenReturn(true);
            Assertions.assertTrue(service.eliminarPublicacion(publicacion.getId()));
            Mockito.verify(repository,Mockito.times(1)).eliminarPublicacion(publicacion.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void eliminarPublicacion_PublicacionNoExiste() {
        try {
            Mockito.when(repository.eliminarPublicacion(publicacion.getId())).thenThrow(SearchItemNotFoundException.class);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->service.eliminarPublicacion(publicacion.getId()));
            Mockito.verify(repository,Mockito.times(1)).eliminarPublicacion(publicacion.getId());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
}
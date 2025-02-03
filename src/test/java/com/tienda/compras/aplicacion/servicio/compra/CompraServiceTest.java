package com.tienda.compras.aplicacion.servicio.compra;

import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.compras.aplicacion.puerto.salida.compra.PuertoSalidaCompra;
import com.tienda.compras.dominio.Compra;
import com.tienda.compras.dominio.Orden;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.publicaciones.dominio.Publicacion;
import com.tienda.usuarios.aplicacion.puerto.entrada.UsuarioPortIn;
import com.tienda.usuarios.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompraServiceTest {
    private Compra domainModel=new Compra();
    private Usuario usuario=new Usuario();
    private Orden orden1=new Orden(
            1,
            new Publicacion(1,"Tomate cherry","Tomate cherry de alta calidad",10.5,new Categoria(1,"frutas"),5, LocalDateTime.now()),
            5,
            new Compra()
    );
    private Orden orden2=new Orden(
            1,
            new Publicacion(2,"Tomate cherry","Tomate cherry de alta calidad",10.5,new Categoria(1,"frutas"),5, LocalDateTime.now()),
            5,
            new Compra()
    );

    private PuertoSalidaCompra repositoryMock=mock(PuertoSalidaCompra.class);
    //private ServicioValidarUsuario servicioValidarUsuarioMock=mock(ServicioValidarUsuario.class);
    private UsuarioPortIn obtenerUsuario=mock(UsuarioPortIn.class);

    private CompraService service;

    @BeforeEach
    void setUp() {
        usuario.setId(1);
        usuario.setDocumento("11111");

        domainModel.setId_compra(1);
        domainModel.setUsuario(usuario);
        domainModel.setOrdenes(List.of(orden1,orden2));
        service=new CompraService(repositoryMock,obtenerUsuario);
    }

    @Test
    void obtenerCompraPorId() {
        try {
            when(repositoryMock.obtenerCompraPorId(domainModel.getId_compra())).thenReturn(domainModel);
            assertEquals(domainModel,service.obtenerCompraPorId(domainModel.getId_compra()));
            verify(repositoryMock,times(1)).obtenerCompraPorId(domainModel.getId_compra());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void obtenerCompraPorId_CompraNoExiste() {
        try {
            when(repositoryMock.obtenerCompraPorId(domainModel.getId_compra())).thenThrow(SearchItemNotFoundException.class);
            assertThrows(SearchItemNotFoundException.class,()->service.obtenerCompraPorId(domainModel.getId_compra()));
            verify(repositoryMock,times(1)).obtenerCompraPorId(domainModel.getId_compra());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void obtenerComprasPorIdUsuario() {
        try {
            when(repositoryMock.obtenerComprasPorIdUsuario(domainModel.getUsuario().getId())).thenReturn(List.of(domainModel));
            when(obtenerUsuario.existePorId(domainModel.getUsuario().getId())).thenReturn(true);
            assertEquals(List.of(domainModel),service.obtenerComprasPorIdUsuario(domainModel.getUsuario().getId()));
            verify(repositoryMock,times(1)).obtenerComprasPorIdUsuario(domainModel.getUsuario().getId());
            verify(obtenerUsuario,times(1)).existePorId(domainModel.getUsuario().getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void obtenerComprasPorIdUsuario_UsuarioNoExiste() {
        try {
            when(repositoryMock.obtenerComprasPorIdUsuario(domainModel.getUsuario().getId())).thenReturn(List.of(domainModel));
            when(obtenerUsuario.existePorId(domainModel.getUsuario().getId())).thenReturn(false);
            assertThrows(SearchItemNotFoundException.class,()->service.obtenerComprasPorIdUsuario(domainModel.getUsuario().getId()));
            verify(repositoryMock,times(0)).obtenerComprasPorIdUsuario(domainModel.getUsuario().getId());
            verify(obtenerUsuario,times(1)).existePorId(domainModel.getUsuario().getId());
        } catch (Exception e) {
            fail(e);
        }
    }
/*
    @Test
    void registrarCompra() {
        try {
            domainModel.setOrdenes(List.of(orden1,orden2));
            when(servicioValidarUsuarioMock.validarUsuarioExistePorId(domainModel.getUsuario().getId())).thenReturn(true);
            when(repositoryMock.registrarCompra(domainModel)).thenReturn(domainModel);
            when(obtenerUsuario.obtenerPorId(domainModel.getUsuario().getId())).thenReturn(usuario);
            Compra response=service.registrarCompra(domainModel.getUsuario().getId(),domainModel.getOrdenes());
            assertEquals(domainModel,response);
            verify(servicioValidarUsuarioMock,times(1)).validarUsuarioExistePorDocumento(domainModel.getUsuario().getDocumento());
            verify(repositoryMock,times(1)).registrarCompra(domainModel);
            verify(obtenerUsuario,times(1)).obtenerPorId(domainModel.getUsuario().getId());
        } catch (Exception e) {
            fail(e);
        }
    }

 */
    @Test
    void registrarCompra_UsuarioNoExiste() {
        try {
            domainModel.setOrdenes(List.of(orden1,orden2));
            when(obtenerUsuario.existePorId(domainModel.getUsuario().getId())).thenReturn(false);
            when(repositoryMock.registrarCompra(domainModel)).thenReturn(domainModel);
            when(obtenerUsuario.obtenerPorId(domainModel.getUsuario().getId())).thenReturn(usuario);
            assertThrows(SearchItemNotFoundException.class,()->service.registrarCompra(domainModel.getUsuario().getId(),domainModel.getOrdenes()));
            verify(obtenerUsuario,times(1)).existePorId(domainModel.getUsuario().getId());
            verify(repositoryMock,times(0)).registrarCompra(domainModel);
            verify(obtenerUsuario,times(0)).obtenerPorId(domainModel.getUsuario().getId());
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void registrarCompra_NoHayOrdenes() {
        try {
            domainModel.setOrdenes(List.of(orden1,orden2));
            when(obtenerUsuario.existePorId(domainModel.getUsuario().getId())).thenReturn(true);
            when(repositoryMock.registrarCompra(domainModel)).thenReturn(domainModel);
            when(obtenerUsuario.obtenerPorId(domainModel.getUsuario().getId())).thenReturn(usuario);
            assertThrows(InvalidInputException.class,()->service.registrarCompra(domainModel.getUsuario().getId(),List.of()));
            verify(obtenerUsuario,times(1)).existePorId(domainModel.getUsuario().getId());
            verify(repositoryMock,times(0)).registrarCompra(domainModel);
            verify(obtenerUsuario,times(0)).obtenerPorId(domainModel.getUsuario().getId());
        } catch (Exception e) {
            fail(e);
        }
    }
}
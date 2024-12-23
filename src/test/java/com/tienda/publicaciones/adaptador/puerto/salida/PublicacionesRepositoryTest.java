package com.tienda.publicaciones.adaptador.puerto.salida;

import com.tienda.categoriasProductos.adaptador.modelo.CategoriaPersistenceModel;
import com.tienda.categoriasProductos.aplicacion.puerto.salida.CategoriaPortOut;
import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.publicaciones.adaptador.modelo.PublicacionPersistenceModel;
import com.tienda.publicaciones.dominio.Publicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

class PublicacionesRepositoryTest {
    private PublicacionesRepository repository=new PublicacionesRepository();
    private CategoriaPortOut persistenceCategoria;
    private PublicacionesCrudRepository persistence;
    private MapperRepositoryToDomainPublicacion mapper;
    private Publicacion publicacionDomainModel;
    private PublicacionPersistenceModel publicacionPersistenceModel;
    private ArrayList<PublicacionPersistenceModel> publicaciones=new ArrayList<>();
    private CategoriaPersistenceModel categoria;
    @BeforeEach
    void setUp(){
        persistence= Mockito.mock(PublicacionesCrudRepository.class);
        persistenceCategoria=Mockito.mock(CategoriaPortOut.class);
        mapper=Mockito.mock(MapperRepositoryToDomainPublicacion.class);
        repository.setRepository(persistence);
        repository.setMapper(mapper);
        repository.setRepositoryCategoria(persistenceCategoria);
        publicacionDomainModel=new Publicacion(1,"Tomate cherry","Tomate cherry de alta calidad",10.5,new Categoria(1,"frutas"),5, LocalDateTime.now());
        categoria=new CategoriaPersistenceModel();
        categoria.setId(1);
        categoria.setNombre("frutas");
        publicacionPersistenceModel=new PublicacionPersistenceModel();
        publicacionPersistenceModel.setIdPublicacion(1);
        publicacionPersistenceModel.setTituloPublicacion("Tomate cherry");
        publicacionPersistenceModel.setDescripcion("Tomate cherry de alta calidad");
        publicacionPersistenceModel.setPrecio(10.5);
        publicacionPersistenceModel.setCategoriaId(categoria);
        publicacionPersistenceModel.setCantidadDisponible(5);
        publicacionPersistenceModel.setFechaPublicacion(LocalDateTime.now());
        //publicaciones.add(publicacionPersistenceModel);
    }

    @Test
    void obtenerPublicaciones() {
        Mockito.when(persistence.findAll()).thenReturn(publicaciones);
        repository.obtenerPublicaciones();
        Mockito.verify(persistence,Mockito.times(1)).findAll();
    }

    @Test
    void obtenerPublicacion() {
        try {
            Mockito.when(persistence.findById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(Optional.ofNullable(publicacionPersistenceModel));
            Mockito.when(mapper.toDomainModel(publicacionPersistenceModel)).thenReturn(publicacionDomainModel);
            Assertions.assertEquals(publicacionDomainModel, repository.obtenerPublicacion(publicacionPersistenceModel.getIdPublicacion()));
            Mockito.verify(persistence,Mockito.times(1)).findById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(mapper,Mockito.times(1)).toDomainModel(publicacionPersistenceModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void obtenerPublicacion_NoExisteLaPublicacion() {
        try {
            Mockito.when(persistence.findById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(Optional.empty());
            Mockito.when(mapper.toDomainModel(publicacionPersistenceModel)).thenReturn(publicacionDomainModel);
            Assertions.assertThrows(SearchItemNotFoundException.class, ()->repository.obtenerPublicacion(publicacionPersistenceModel.getIdPublicacion()));
            Mockito.verify(persistence,Mockito.times(1)).findById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(mapper,Mockito.times(0)).toDomainModel(publicacionPersistenceModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void crearPublicacion() {
        try {
            Mockito.when(persistence.existsById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(false);
            Mockito.when(persistence.save(publicacionPersistenceModel)).thenReturn(publicacionPersistenceModel);
            Mockito.when(persistenceCategoria.existePorId(publicacionPersistenceModel.getIdPublicacion())).thenReturn(true);
            Mockito.when(mapper.toDomainModel(publicacionPersistenceModel)).thenReturn(publicacionDomainModel);
            Mockito.when(mapper.toPersistenceModel(publicacionDomainModel)).thenReturn(publicacionPersistenceModel);
            Assertions.assertEquals(publicacionDomainModel,repository.crearPublicacion(publicacionDomainModel));
            Mockito.verify(persistence,Mockito.times(1)).existsById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(persistence,Mockito.times(1)).save(publicacionPersistenceModel);
            Mockito.verify(persistenceCategoria,Mockito.times(1)).existePorId(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(mapper,Mockito.times(1)).toDomainModel(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(1)).toPersistenceModel(publicacionDomainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearPublicacion_PublicacionYaExiste() {
        try {
            Mockito.when(persistenceCategoria.existePorId(publicacionPersistenceModel.getIdPublicacion())).thenReturn(true);
            Mockito.when(persistence.existsById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(true);
            Mockito.when(persistence.save(publicacionPersistenceModel)).thenReturn(publicacionPersistenceModel);
            Mockito.when(mapper.toDomainModel(publicacionPersistenceModel)).thenReturn(publicacionDomainModel);
            Mockito.when(mapper.toPersistenceModel(publicacionDomainModel)).thenReturn(publicacionPersistenceModel);
            Assertions.assertThrows(ItemAlreadyExistException.class,()->repository.crearPublicacion(publicacionDomainModel));
            Mockito.verify(persistence,Mockito.times(1)).existsById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(persistence,Mockito.times(0)).save(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(0)).toDomainModel(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(0)).toPersistenceModel(publicacionDomainModel);
            Mockito.verify(persistenceCategoria,Mockito.times(1)).existePorId(publicacionPersistenceModel.getIdPublicacion());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void crearPublicacion_CategoriaNoExiste() {
        try {
            Mockito.when(persistence.existsById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(false);
            Mockito.when(persistence.save(publicacionPersistenceModel)).thenReturn(publicacionPersistenceModel);
            Mockito.when(persistenceCategoria.existePorId(publicacionPersistenceModel.getIdPublicacion())).thenReturn(false);
            Mockito.when(mapper.toDomainModel(publicacionPersistenceModel)).thenReturn(publicacionDomainModel);
            Mockito.when(mapper.toPersistenceModel(publicacionDomainModel)).thenReturn(publicacionPersistenceModel);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->repository.crearPublicacion(publicacionDomainModel));
            Mockito.verify(persistence,Mockito.times(0)).existsById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(persistence,Mockito.times(0)).save(publicacionPersistenceModel);
            Mockito.verify(persistenceCategoria,Mockito.times(1)).existePorId(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(mapper,Mockito.times(0)).toDomainModel(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(0)).toPersistenceModel(publicacionDomainModel);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void actualizarPublicacion() {
        try {
            Mockito.when(persistenceCategoria.existePorId(publicacionPersistenceModel.getIdPublicacion())).thenReturn(true);
            Mockito.when(persistence.existsById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(true);
            Mockito.when(persistence.save(publicacionPersistenceModel)).thenReturn(publicacionPersistenceModel);
            Mockito.when(mapper.toDomainModel(publicacionPersistenceModel)).thenReturn(publicacionDomainModel);
            Mockito.when(mapper.toPersistenceModel(publicacionDomainModel)).thenReturn(publicacionPersistenceModel);
            Assertions.assertEquals(publicacionDomainModel,repository.actualizarPublicacion(publicacionDomainModel));
            Mockito.verify(persistence,Mockito.times(1)).existsById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(persistence,Mockito.times(1)).save(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(1)).toDomainModel(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(1)).toPersistenceModel(publicacionDomainModel);
            Mockito.verify(persistenceCategoria,Mockito.times(1)).existePorId(publicacionPersistenceModel.getIdPublicacion());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarPublicacion_PublicacionNoExiste() {
        try {
            Mockito.when(persistenceCategoria.existePorId(publicacionPersistenceModel.getIdPublicacion())).thenReturn(true);
            Mockito.when(persistence.existsById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(false);
            Mockito.when(persistence.save(publicacionPersistenceModel)).thenReturn(publicacionPersistenceModel);
            Mockito.when(mapper.toDomainModel(publicacionPersistenceModel)).thenReturn(publicacionDomainModel);
            Mockito.when(mapper.toPersistenceModel(publicacionDomainModel)).thenReturn(publicacionPersistenceModel);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->repository.actualizarPublicacion(publicacionDomainModel));
            Mockito.verify(persistence,Mockito.times(1)).existsById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(persistence,Mockito.times(0)).save(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(0)).toDomainModel(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(0)).toPersistenceModel(publicacionDomainModel);
            Mockito.verify(persistenceCategoria,Mockito.times(1)).existePorId(publicacionPersistenceModel.getIdPublicacion());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void actualizarPublicacion_CategoriaNoExiste() {
        try {
            Mockito.when(persistenceCategoria.existePorId(publicacionPersistenceModel.getIdPublicacion())).thenReturn(false);
            Mockito.when(persistence.existsById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(true);
            Mockito.when(persistence.save(publicacionPersistenceModel)).thenReturn(publicacionPersistenceModel);
            Mockito.when(mapper.toDomainModel(publicacionPersistenceModel)).thenReturn(publicacionDomainModel);
            Mockito.when(mapper.toPersistenceModel(publicacionDomainModel)).thenReturn(publicacionPersistenceModel);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->repository.actualizarPublicacion(publicacionDomainModel));
            Mockito.verify(persistence,Mockito.times(0)).existsById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(persistence,Mockito.times(0)).save(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(0)).toDomainModel(publicacionPersistenceModel);
            Mockito.verify(mapper,Mockito.times(0)).toPersistenceModel(publicacionDomainModel);
            Mockito.verify(persistenceCategoria,Mockito.times(1)).existePorId(publicacionPersistenceModel.getIdPublicacion());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void eliminarPublicacion() {
        try {
            Mockito.when(persistence.existsById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(true);
            Assertions.assertTrue(repository.eliminarPublicacion(publicacionPersistenceModel.getIdPublicacion()));
            Mockito.verify(persistence,Mockito.times(1)).existsById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(persistence,Mockito.times(1)).deleteById(publicacionPersistenceModel.getIdPublicacion());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    void eliminarPublicacion_PublicacionNoExiste() {
        try {
            Mockito.when(persistence.existsById(publicacionPersistenceModel.getIdPublicacion())).thenReturn(false);
            Assertions.assertThrows(SearchItemNotFoundException.class,()->repository.eliminarPublicacion(publicacionPersistenceModel.getIdPublicacion()));
            Mockito.verify(persistence,Mockito.times(1)).existsById(publicacionPersistenceModel.getIdPublicacion());
            Mockito.verify(persistence,Mockito.times(0)).deleteById(publicacionPersistenceModel.getIdPublicacion());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
}
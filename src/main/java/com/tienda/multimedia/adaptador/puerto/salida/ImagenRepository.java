package com.tienda.multimedia.adaptador.puerto.salida;

import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.multimedia.adaptador.modelo.persistencia.ImagenProductoPersistenceModel;
import com.tienda.multimedia.aplicacion.puerto.salida.ImagenPortOut;
import com.tienda.multimedia.domino.ImagenProductoDomainModel;
import com.tienda.publicaciones.adaptador.modelo.PublicacionPersistenceModel;
import com.tienda.publicaciones.adaptador.puerto.salida.PublicacionesCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ImagenRepository implements ImagenPortOut {
    private ImagenCrudRepository crudRepository;
    private MapperRepositoryToDomainImagen mapper;
    private PublicacionesCrudRepository publicacionesCrudRepository;

    /*-----------------------------------------------------------------*/
    @Autowired
    public void setCrudRepository(ImagenCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Autowired
    public void setMapper(MapperRepositoryToDomainImagen mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setPublicacionesCrudRepository(PublicacionesCrudRepository publicacionesCrudRepository) {
        this.publicacionesCrudRepository = publicacionesCrudRepository;
    }
    /*-----------------------------------------------------------------*/

    @Override
    public List<ImagenProductoDomainModel> consultarImagenesPorPublicacion(int idPublicacion) {
        List<ImagenProductoDomainModel> response=new ArrayList<>();
        crudRepository.findByPublicacion_idPublicacion((long) idPublicacion).iterator().forEachRemaining(
                (i)->response.add(
                        mapper.toDomainModel(i)
                )
        );
        return response;
    }

    @Override
    public ImagenProductoDomainModel consultarImagenPorId(int idImagen) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                crudRepository.findById(idImagen).orElseThrow(
                        ()->new SearchItemNotFoundException("No se ha encontrado la imagen")
                )
        );
    }

    @Override
    public boolean guardarImagen(ImagenProductoDomainModel imagen, int idPublicacion) throws ItemAlreadyExistException, SearchItemNotFoundException {
        if (crudRepository.existsById(imagen.getId())){
            throw new ItemAlreadyExistException("Ya existe un registro de una imagen con este id");
        }
        System.out.println("imagen repository 65: "+idPublicacion);
        PublicacionPersistenceModel p=publicacionesCrudRepository.findById(idPublicacion)
                .orElseThrow(()->new SearchItemNotFoundException("no existe la publicacion que se le trata de asignar a la imagen"));
        ImagenProductoPersistenceModel i=mapper.toPersistenceModel(imagen);
        i.setPublicacion(p);
        ImagenProductoPersistenceModel response=crudRepository.save(i);
        return !(response.getId()<=0);
    }

    @Override
    public boolean guardarImagen(String imagen, int idPublicacion) throws ItemAlreadyExistException, SearchItemNotFoundException {
        if (!publicacionesCrudRepository.existsById(idPublicacion)){
            throw new SearchItemNotFoundException("No existe la publicacion que se esta tratando de asociar");
        }
        PublicacionPersistenceModel p=publicacionesCrudRepository.findById(idPublicacion).get();
        ImagenProductoPersistenceModel i=new ImagenProductoPersistenceModel();
        i.setNombreArchivo(imagen);
        i.setPublicacion(p);
        ImagenProductoPersistenceModel response=crudRepository.save(i);
        return !(response.getId()<=0);
    }

    @Override
    public boolean eliminarImagenPorId(int idImagen) throws SearchItemNotFoundException {
        if (!crudRepository.existsById(idImagen)){
            throw new SearchItemNotFoundException("La imagen no se elimino, No se encontro un registro con este id");
        }
        crudRepository.deleteById(idImagen);
        return true;
    }

    @Override
    public boolean imagenExiste(int idImagen) {
        return crudRepository.existsById(idImagen);
    }

}

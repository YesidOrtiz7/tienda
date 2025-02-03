package com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.persistencia.RolEntity;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoRol;
import com.tienda.usuarios.dominio.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RolRepository implements PuertoRol {
    private final RolCrudRepository repository;
    private final MapperRepositoryToDomainRol mapper;

    @Autowired
    public RolRepository(RolCrudRepository repository, MapperRepositoryToDomainRol mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Rol obtenerRolPorId(int id) throws InvalidInputException {
        return mapper.toDomainModel(
                repository.findById(id).orElseThrow(()-> new InvalidInputException("No existe el rol"))
        );
    }

    @Override
    public Rol nuevoRol(Rol rol) throws ItemAlreadyExistException {
        if (repository.existsById(rol.getId())){
            throw new ItemAlreadyExistException("Ya existe un rol con este id");
        }
        RolEntity rolPeristence= mapper.toPersistenceModel(rol);
        return mapper.toDomainModel(
                repository.save(rolPeristence)
        );
    }

    @Override
    public boolean eliminarRol(int id) throws SearchItemNotFoundException{
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        throw new SearchItemNotFoundException("El rol no existe");
    }

    @Override
    public Rol actualizarRol(Rol rol) throws SearchItemNotFoundException {
        if (!repository.existsById(rol.getId())){
            throw new SearchItemNotFoundException("No existe un rol con este id");
        }
        return mapper.toDomainModel(
                repository.save(
                        mapper.toPersistenceModel(rol)
                )
        );
    }

    @Override
    public List<Rol> obtenerRoles() {
        List<Rol> response=new ArrayList<>();
        repository.findAll().forEach(
                (i)->response.add(mapper.toDomainModel(i))
        );
        return response;
    }
}

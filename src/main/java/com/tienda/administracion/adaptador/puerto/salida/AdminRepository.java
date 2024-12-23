package com.tienda.administracion.adaptador.puerto.salida;

import com.tienda.administracion.adaptador.modelo.AdministradorPersistenceModel;
import com.tienda.administracion.aplicacion.puerto.salida.PuertoSalidaAdmin;
import com.tienda.administracion.dominio.Administrador;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class AdminRepository implements PuertoSalidaAdmin {
    private AdminCrudRepository repository;
    private MapperRepositoryToDomainAdmin mapper;
    private PasswordEncoder passwordEncoder;

    /*-------------------------------------------------------------*/
    @Autowired
    public void setRepository(AdminCrudRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(MapperRepositoryToDomainAdmin mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    /*-------------------------------------------------------------*/

    @Override
    public boolean existById(int id) {
        return repository.existsById(id);
    }

    @Override
    public ArrayList<Administrador> obtenerAdmins() {
        ArrayList<Administrador> response=new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(
                (i)->{response.add(mapper.toDomainModel(i));}
        );
        return response;
    }

    @Override
    public Administrador obtenerAdminPorId(int id) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                repository.findById(id).orElseThrow(()->new SearchItemNotFoundException("Este perfil administrador no existe"))
        );
    }

    @Override
    public Administrador registrarAdmin(Administrador admin) throws ItemAlreadyExistException {
        if (repository.existsById(admin.getId())){
            throw new ItemAlreadyExistException("Ya existe un administrador con este id");
        }
        String passwordEncoded=this.passwordEncoder.encode(admin.getContrasena());
        admin.setContrasena(passwordEncoded);
        AdministradorPersistenceModel response=repository.save(
                mapper.toPersistenceModel(admin)
        );
        return mapper.toDomainModel(response);
    }

    @Override
    public Administrador actualizarAdmin(Administrador admin) throws SearchItemNotFoundException {
        if (repository.existsById(admin.getId())){
            AdministradorPersistenceModel response=repository.save(mapper.toPersistenceModel(admin));
            return mapper.toDomainModel(response);
        }
        String passwordEncoded=this.passwordEncoder.encode(admin.getContrasena());
        admin.setContrasena(passwordEncoded);
        throw new SearchItemNotFoundException("El administrador a actualizar no existe");
    }

    @Override
    public boolean eliminarAdminPorId(int id) throws SearchItemNotFoundException {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        throw new SearchItemNotFoundException("La cuenta de administrador no existe");
    }
    @Override
    public Administrador findByDocument(String documento) throws SearchItemNotFoundException {
        return mapper.toDomainModel(repository.findByDocumento(documento).orElseThrow(()->new SearchItemNotFoundException("No se encuentra el administrador")));
    }
}

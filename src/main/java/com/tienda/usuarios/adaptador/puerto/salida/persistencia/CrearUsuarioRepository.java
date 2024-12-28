package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol.MapperRepositoryToDomainRol;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoCrearUsuario;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class CrearUsuarioRepository implements PuertoCrearUsuario {
    private UsuarioCrudRepository repository;
    private MapperRepositoryToDomainUsuario mapper;
    private MapperRepositoryToDomainRol mapperRol;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public void setMapper(MapperRepositoryToDomainUsuario mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setMapperRol(MapperRepositoryToDomainRol mapperRol) {
        this.mapperRol = mapperRol;
    }

    @Autowired
    public void setRepository(UsuarioCrudRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws InvalidInputException {
        if (usuario.getContrasena().length()<8){
            throw new InvalidInputException("La contraseña debe tener almenos 8 caracteres");
        }
        if (usuario.getCorreo().isBlank()){
            throw new InvalidInputException("El campo de correo esta vacio");
        }
        usuario.setBloqueado(false);
        usuario.setEliminado(false);
        String passwordEncode= this.passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(passwordEncode);

        UsuarioPersistenceModel persistenceModel =mapper.toPersistenceModel(usuario);

        persistenceModel=repository.save(persistenceModel);
        return mapper.toDomainModel(persistenceModel);
    }
}

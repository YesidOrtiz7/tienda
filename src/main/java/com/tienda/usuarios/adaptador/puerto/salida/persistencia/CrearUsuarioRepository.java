package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.cuentas.aplicacion.puerto.salida.PuertoSalidaCuenta;
import com.tienda.cuentas.dominio.Cuenta;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
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
    private PuertoSalidaCuenta cuentaRepository;
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

    @Autowired
    public void setCuentaRepository(PuertoSalidaCuenta cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws InvalidInputException, SearchItemNotFoundException {
        if (usuario.getContrasena().length()<8){
            throw new InvalidInputException("La contraseña debe tener almenos 8 caracteres");
        }
        if (usuario.getCorreo().isBlank()){
            throw new InvalidInputException("El campo de correo esta vacio");
        }
        if (repository.existsByDocumento(usuario.getDocumento())){
            throw new InvalidInputException("Ya existe un usuario con este documento de identidad");
        }
        usuario.setBloqueado(false);
        usuario.setEliminado(false);
        String passwordEncode= this.passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(passwordEncode);

        UsuarioPersistenceModel persistenceModel =mapper.toPersistenceModel(usuario);

        persistenceModel=repository.save(persistenceModel);
        System.out.println("Id del usuario registrado"+persistenceModel.getId());
        Cuenta cuentaUsuario=new Cuenta(persistenceModel.getId(), 0);
        cuentaRepository.crearCuenta(cuentaUsuario);
        return mapper.toDomainModel(persistenceModel);
    }
}

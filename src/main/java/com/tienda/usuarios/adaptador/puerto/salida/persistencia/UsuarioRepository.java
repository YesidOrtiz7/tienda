package com.tienda.usuarios.adaptador.puerto.salida.persistencia;

import com.tienda.cuentas.aplicacion.puerto.salida.PuertoSalidaCuenta;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.rol.UsuarioRolRepository;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository implements PuertoSalidaUsuario {
    private UsuarioCrudRepository repository;
    private MapperRepositoryToDomainUsuario mapper;
    private PasswordEncoder passwordEncoder;
    private PuertoSalidaCuenta cuentaRepository;
    private UsuarioRolRepository usuarioRolRepository;

    /*-------------------------dependencias---------------------------*/
    @Autowired
    public void setRepository(UsuarioCrudRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(MapperRepositoryToDomainUsuario mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    @Autowired
    public void setCuentaRepository(PuertoSalidaCuenta cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Autowired
    public void setUsuarioRolRepository(UsuarioRolRepository usuarioRolRepository) {
        this.usuarioRolRepository = usuarioRolRepository;
    }
    /*----------------------------------------------------------------*/

    @Override
    public boolean existById(int id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existByDocumento(String documento) {
        return repository.existsByDocumento(documento);
    }

    @Override
    public Usuario getById(int id) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                repository.findById(id).orElseThrow(()->new SearchItemNotFoundException("El usuario no existe"))
        );
    }

    @Override
    public Usuario getByDocument(String document) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                repository.findByDocumento(document).orElseThrow(()->new SearchItemNotFoundException("El usuario no existe"))
        );
    }

    @Override
    public Usuario crearUsuario(Usuario usuario, int rol) throws InvalidInputException, SearchItemNotFoundException, ItemAlreadyExistException {
        if (usuario.getId()<=0 && repository.existsById(usuario.getId())){
            throw new ItemAlreadyExistException("Ya existe un usuario con este id");
        }
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

        //creando cuenta del usuario
        persistenceModel=repository.save(persistenceModel);
        System.out.println("Id del usuario registrado"+persistenceModel.getId());
        cuentaRepository.crearCuenta(persistenceModel.getId());

        //asignando el rol al usuario
        usuarioRolRepository.insertarUsuarioRol(rol,persistenceModel.getId());

        return mapper.toDomainModel(persistenceModel);
    }

    @Override
    public Usuario crearUsuario_sinCuenta(Usuario usuario,int rol) throws InvalidInputException, ItemAlreadyExistException {
        if (usuario.getId()<=0 && repository.existsById(usuario.getId())){
            throw new ItemAlreadyExistException("Ya existe un usuario con este id");
        }
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

        //asignando el rol al usuario
        usuarioRolRepository.insertarUsuarioRol(rol,persistenceModel.getId());

        return mapper.toDomainModel(
                repository.save(persistenceModel)
        );
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws InvalidInputException, ItemAlreadyExistException {
        return null;
    }

    @Override
    public boolean deleteById(int id) throws SearchItemNotFoundException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

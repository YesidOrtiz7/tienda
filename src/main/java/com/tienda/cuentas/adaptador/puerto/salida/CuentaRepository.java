package com.tienda.cuentas.adaptador.puerto.salida;

import com.tienda.cuentas.adaptador.puerto.salida.modelos.CuentaEntity;
import com.tienda.cuentas.aplicacion.puerto.salida.PuertoSalidaCuenta;
import com.tienda.cuentas.dominio.Cuenta;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.MapperRepositoryToDomainUsuario;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.UsuarioCrudRepository;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CuentaRepository implements PuertoSalidaCuenta {
    private CuentaCrudRepository repository;
    private MapperRepositoryToDomainCuenta mapper;
    private MapperRepositoryToDomainUsuario mapperUsuario;
    private UsuarioRepository usuarioRepository;
    private UsuarioCrudRepository usuarioCrudRepository;

    public CuentaRepository() {
    }

    @Autowired
    public CuentaRepository(CuentaCrudRepository repository, MapperRepositoryToDomainCuenta mapper, MapperRepositoryToDomainUsuario mapperUsuario, UsuarioRepository usuarioRepository, UsuarioCrudRepository usuarioCrudRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.mapperUsuario = mapperUsuario;
        this.usuarioRepository = usuarioRepository;
        this.usuarioCrudRepository = usuarioCrudRepository;
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) throws SearchItemNotFoundException, InvalidInputException {
        if (!usuarioRepository.existById(cuenta.getId_usuario())){
            throw new SearchItemNotFoundException("No existe el cliente");
        }
        if (repository.existsById(cuenta.getId_usuario())){
            throw new InvalidInputException("Ya existe una cuenta para este usuario");
        }

        UsuarioPersistenceModel usuario= usuarioCrudRepository.findById(cuenta.getId_usuario())
                .orElseThrow(() -> new SearchItemNotFoundException("No se pudo encontrar el usuario"));
        if (usuario == null) {
            throw new SearchItemNotFoundException("No se pudo encontrar el usuario");
        }

        CuentaEntity cuentaEntity = new CuentaEntity();
        cuentaEntity.setSaldo(0);
        cuentaEntity.setUsuario(usuario);

        cuentaEntity = repository.save(cuentaEntity);

        return mapper.toDomainModel(cuentaEntity);
    }

    @Override
    public Cuenta obtenerCuentaPorIdUsuario(int id) throws SearchItemNotFoundException {
        return mapper.toDomainModel(
                repository.findById(id).orElseThrow(()->new SearchItemNotFoundException("No existe la cuenta"))
        );
    }

    @Override
    public Cuenta actualizarSaldoCuenta(Cuenta cuenta) throws SearchItemNotFoundException {
        if (!repository.existsById(cuenta.getId_usuario())){
            throw new SearchItemNotFoundException("La cuenta no existe");
        }
        CuentaEntity response= repository.save(mapper.toPersistenceModel(cuenta));
        return mapper.toDomainModel(response);
    }
}

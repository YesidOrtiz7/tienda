package com.tienda.cuentas.adaptador.puerto.salida;

import com.tienda.cuentas.adaptador.puerto.salida.modelos.CuentaEntity;
import com.tienda.cuentas.aplicacion.puerto.salida.PuertoSalidaCuenta;
import com.tienda.cuentas.dominio.Cuenta;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.puerto.salida.persistencia.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CuentaRepository implements PuertoSalidaCuenta {
    private CuentaCrudRepository repository;
    private MapperRepositoryToDomainCuenta mapper;
    private UsuarioRepository usuarioRepository;

    public CuentaRepository() {
    }

    @Autowired
    public CuentaRepository(CuentaCrudRepository repository, MapperRepositoryToDomainCuenta mapper, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) throws SearchItemNotFoundException, InvalidInputException {
        if (!usuarioRepository.existById(cuenta.getId_usuario())){
            throw new SearchItemNotFoundException("No existe el cliente");
        }
        if (repository.existsById(cuenta.getId_usuario())){
            throw new InvalidInputException("Ya existe una cuenta para este usuario");
        }
        return mapper.toDomainModel(
                repository.save(
                        mapper.toPersistenceModel(cuenta)
                )
        );
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

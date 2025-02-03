package com.tienda.cuentas.aplicacion.puerto.salida;

import com.tienda.cuentas.dominio.Cuenta;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;

public interface PuertoSalidaCuenta {
    Cuenta crearCuenta(int id) throws SearchItemNotFoundException, InvalidInputException;
    Cuenta obtenerCuentaPorIdUsuario(int id) throws SearchItemNotFoundException;
    Cuenta actualizarSaldoCuenta(Cuenta cuenta) throws SearchItemNotFoundException;
    boolean existById(int id);
}

package com.tienda.userSecurityService.aplicacion.servicio;

import com.tienda.administracion.adaptador.modelo.AdministradorPersistenceModel;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.userSecurityService.aplicacion.puerto.salida.FindAdminByDoc;
import com.tienda.userSecurityService.aplicacion.puerto.salida.FindUsuarioByDoc;
import com.tienda.usuarios.adaptador.modelo.UsuarioPersistenceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {
    private FindAdminByDoc adminRepository;
    private FindUsuarioByDoc usuarioRepository;

    @Autowired
    public UserSecurityService(FindAdminByDoc adminRepository, FindUsuarioByDoc usuarioRepository) {
        this.adminRepository = adminRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioPersistenceModel> usuario= usuarioRepository.findByDoc(username);
        if (usuario.isPresent()){
            return User.builder()
                    .username(usuario.get().getUserName())
                    .password(usuario.get().getContrasena())
                    .roles("USUARIO")
                    .accountLocked(!usuario.get().isHabilitado())
                    //.disable()
                    .build();
        }
        Optional<AdministradorPersistenceModel> administrador=adminRepository.findByDoc(username);
        if (administrador.isPresent()){
            return User.builder()
                    .username(administrador.get().getUserName())
                    .password(administrador.get().getContrasena())
                    .roles("ADMIN")
                    .accountLocked(!administrador.get().isHabilitado())
                    .build();
        }
        throw new UsernameNotFoundException("No se encontro el usuario");
    }
}

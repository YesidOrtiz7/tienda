package com.tienda.userSecurityService.aplicacion.servicio;

import com.tienda.userSecurityService.aplicacion.puerto.salida.FindUsuarioByDoc;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {
    private FindUsuarioByDoc usuarioRepository;

    @Autowired
    public UserSecurityService(FindUsuarioByDoc usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**carga el usuario con el documento de identididad especificado*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioPersistenceModel usuario=usuarioRepository.findByDoc(username).orElseThrow(()->new UsernameNotFoundException("No se encontro el usuario"));
        ArrayList<String> roles=new ArrayList<>();
        usuario.getUsuarioRoles().forEach((i)-> roles.add(i.getRol().getNombre()));
        return User.builder()
                .username(usuario.getUserName())
                .password(usuario.getContrasena())
                .authorities(this.grantedAuthorities(roles.toArray(String[]::new)))
                .accountLocked(usuario.isBloqueado())
                .disabled(usuario.isEliminado())
                .build();
    }
    /**especificar que permisos van a tener los diferentes roles*/
    private String [] getAuthorities(String role){
        if ("ADMIN".equals(role)||"USUARIO".equals(role)){
            return new String[] {};
        }
        return new String[] {};
    }
    /**retorna la lista de roles y autorizaciones concedidas al usuario*/
    private List<GrantedAuthority> grantedAuthorities(String[] roles){
        List<GrantedAuthority> authorities=new ArrayList<>(roles.length);
        for (String role:roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
            for (String authority:this.getAuthorities(role)){
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }
}

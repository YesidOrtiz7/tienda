package com.tienda.webConfigSecurity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    /*implementando la herencia de OncePerRequestFilter podemos interceptar todas las peticiones que se realicen a la
    * api y validarlas*/

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //validamos que la autorizacion en el header de la peticion sea valida
        String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader==null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        //validamos que el jwt sea valido
        String jwt=authHeader.split(" ")[1].trim();
        if (!this.jwtUtil.isValid(jwt)){
            filterChain.doFilter(request,response);
            return;
        }
        //cargar el usuario del userDetailsService (verificar que no este bloqueado)
        String userName=this.jwtUtil.getUserName(jwt);
        User user= (User) this.userDetailsService.loadUserByUsername(userName);

        //cargar al usuario en el contexto de seguridad de spring
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}

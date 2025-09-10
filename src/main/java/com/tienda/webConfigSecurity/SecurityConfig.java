package com.tienda.webConfigSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*deshabilitamos la proteccion CORS para hacer nuestra aplicacion stateless y que funcione con JWT,
        * es decir, nuestra aplicacion no almacenara ninguna sesion, no tendra estado y basaremos su acceso en tokens*/
        /*la autorizacion no se tomara desde una cookie ya que eso es un riesgo de seguridad*/
        /*los metodos que por defecto tienen proteccion CRSF son los metodos POST,PUT y DELETE*/
        http.authorizeHttpRequests(
                (customizeRequest)->{
                    /*dentro del patron del requestMatcher un * significa que estamos aplicando esta regla a solo
                    * el primer nivel del arbol de rutas, y dos ** significa que estamos aplicando esta regla a todas
                    * las rutas del arbol a partir del sufijo. Las autorizaciones deben ir de lo general a lo especifico*/
                    customizeRequest.requestMatchers("/api/auth/**").permitAll();

                    customizeRequest.requestMatchers(HttpMethod.GET,"/imagenes/*").permitAll();

                    customizeRequest.requestMatchers(HttpMethod.POST,"/productos/*").hasAnyRole("USUARIO");
                    customizeRequest.requestMatchers(HttpMethod.DELETE,"/productos/*").hasRole("ADMIN");
                    customizeRequest.requestMatchers(HttpMethod.GET,"/productos/*").permitAll();
                    customizeRequest.requestMatchers(HttpMethod.PUT,"/productos/*").hasAnyRole("USUARIO");

                    customizeRequest.requestMatchers(HttpMethod.GET,"/categorias/*").hasAnyRole("ADMIN","USUARIO");
                    customizeRequest.requestMatchers(HttpMethod.POST,"/categorias/*").hasRole("ADMIN");
                    customizeRequest.requestMatchers(HttpMethod.PUT,"/categorias/*").hasRole("ADMIN");
                    customizeRequest.requestMatchers(HttpMethod.DELETE,"/categorias/*").hasRole("ADMIN");

                    customizeRequest.requestMatchers(HttpMethod.POST,"/usuarios/inhabilitar").hasRole("ADMIN");
                    customizeRequest.requestMatchers(HttpMethod.POST,"/usuarios/nuevousuario").permitAll();

                    customizeRequest.requestMatchers(HttpMethod.DELETE,"/admin/**").hasRole("ADMIN");
                    customizeRequest.requestMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMIN");
                    customizeRequest.requestMatchers(HttpMethod.PUT,"/admin/**").hasRole("ADMIN");
                    customizeRequest.requestMatchers(HttpMethod.GET,"/admin/**").hasRole("ADMIN");

                    customizeRequest.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();

                    customizeRequest.anyRequest().authenticated();
                }
            )
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
                /*habilitamos cors para que la aplicacion pueda recibir peticiones de varios origenes
                 * el metodo rest tendra que ser anotado con @CrossOrigin(origins="url del frontend")
                 * pero para no tener que anotar asi cada metodo, se crea una configuracion global la cual estara en
                 * el archivo CorsConfig que esta en la carpeta config*/
            .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*con esta anotacion convertimos la aplicacion en stateless y cada peticion se va atratar como si
                * fuera una sesion completamente nueva*/
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /*con este bean especificamos cual password encoder queremos utilizar*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

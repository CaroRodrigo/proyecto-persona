/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eggcurso.holamundo.servicios;

import com.eggcurso.holamundo.entidades.Persona;
import com.eggcurso.holamundo.entidades.Usuario;
import com.eggcurso.holamundo.excepciones.WebException;
import com.eggcurso.holamundo.repositoritorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private PersonaServicio personaServicio;
    
    @Transactional
    public Usuario save(String username, String password, String password2,String dni) throws WebException{
        Usuario usuario = new Usuario();
        
        if (dni == null || dni.isEmpty()) {
            throw new WebException("El dni no puede estar vacio");
        }
        
        
        Persona persona = (Persona) personaServicio.findByDni(dni);
        
        if (persona == null) {
            throw new WebException("No se puede registrar un usuario con un DNI que no exista en la base de datos");
        }
        
        if (findByUserName(username) != null) {
            throw new WebException("Ya existe un usuario con nick intente con otro");
        }
        
        if (username == null || username.isEmpty()) {
            throw new WebException("El usuario no puede estar vacio");
        }
        
        if (password == null || password2 == null || password.isEmpty() || password2.isEmpty()) {
            throw new WebException("La contraseña no puede estar vacia");
        }
        
        if (!password.equals(password2)) {
            throw new WebException("Las contraseñas deben ser iguales");
        }
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setId(persona.getId());
        usuario.setDni(persona.getDni());
        usuario.setNombre(persona.getNombre());
        usuario.setApellido(persona.getApellido());
        usuario.setEdad(persona.getEdad());
        usuario.setCiudad(persona.getCiudad());
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password));
        personaServicio.delete(persona);
        
        return usuarioRepositorio.save(usuario);
    }
    
    public List<Usuario> listAll() {
        return usuarioRepositorio.findAll();
    }

    public List<Usuario> listAll(String q) {
       // return usuarioRepositorio.findAll("%" + q + "%");
       return usuarioRepositorio.findAll();
    }
    
    public Usuario findByUserName(String username){
        return usuarioRepositorio.findByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepositorio.findByUserName(username);
            User user;
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("CLIENTE"));
            return new User(username, usuario.getPassword(), authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
    }
    
}

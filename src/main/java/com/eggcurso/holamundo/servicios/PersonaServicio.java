
package com.eggcurso.holamundo.servicios;

import com.eggcurso.holamundo.entidades.Ciudad;
import com.eggcurso.holamundo.entidades.Persona;
import com.eggcurso.holamundo.excepciones.WebException;
import com.eggcurso.holamundo.repositoritorios.PersonaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaServicio {
    
    @Autowired
    private PersonaRepositorio personaRepositorio;
    
    @Autowired
    private CiudadServicio ciudadServicio;
    
    public Persona save(Persona persona) throws WebException{
        if (findByDni(persona.getDni()) != null) {
             throw new WebException("No se puede registrar una persona con un DNI ya existente");
        }
        if (persona.getNombre().isEmpty() || persona.getNombre() == null) {
            throw new WebException("El nombre no puede estar vacio");
        }
        if (persona.getApellido().isEmpty() || persona.getApellido() == null) {
            throw new WebException("El apellido no puede estar vacio");
        }
        if (persona.getEdad() == null || persona.getEdad() < 1) {
            throw new WebException("La edad no puede estar vacia o ser menos a 1");
        }
        if (persona.getDni()== null || persona.getDni().isEmpty()) {
            throw new WebException("La edad no puede estar vacia o ser menos a 1");
        }
        if (persona.getCiudad() == null) {
            throw new WebException("La ciudad no puede ser nula");
        }else{
            persona.setCiudad(ciudadServicio.findById(persona.getCiudad()));
        }
        return personaRepositorio.save(persona);
    }
    
    @Transactional
    public Persona save(String nombre, String apellido, Integer edad){
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setEdad(edad);
        
        return personaRepositorio.save(persona);
    }
    
    public List<Persona> listall(){
        return personaRepositorio.findAll();
    }
    
    public List<Persona> listallByCiudad(String nombre){
        return personaRepositorio.findAllByCiudad(nombre);
    }
    
    public List<Persona> listallByQ(String q){
        return personaRepositorio.findAllByQ("%"+q+"%");
    }
    
    public Persona findByDni(String dni){
        return personaRepositorio.findAllByDni(dni);
    }
    
    public Optional<Persona> findById(String id){
        return personaRepositorio.findById(id);
    }
    
    @Transactional
    public void delete(Persona persona){
        personaRepositorio.delete(persona);
    }
    
    @Transactional
    public void deleteFieldCiudad(Ciudad ciudad){
        List<Persona> persona = personaRepositorio.findAllByCiudad(ciudad.getNombre());
        for (Persona persona1 : persona) {
            persona1.setCiudad(null);
        }
        personaRepositorio.saveAll(persona);
    }
    
    @Transactional
    public void deleteById(String id){
        Optional<Persona> optional = personaRepositorio.findById(id);
        if (optional.isPresent()) {
            personaRepositorio.delete(optional.get());
        }
    }
    
    
}

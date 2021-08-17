/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eggcurso.holamundo.servicios;

import com.eggcurso.holamundo.entidades.Ciudad;
import com.eggcurso.holamundo.entidades.Ciudad;
import com.eggcurso.holamundo.excepciones.WebException;
import com.eggcurso.holamundo.repositoritorios.CiudadRepocitorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadServicio {

    @Autowired
    private CiudadRepocitorio ciudadRepositorio;
    
    @Autowired
    private PersonaServicio personaServicio;

    public Ciudad save(String nombre) {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(nombre);
        return ciudadRepositorio.save(ciudad);
    }

    public Ciudad save2(Ciudad ciudad) throws WebException {
        if (ciudad.getNombre().isEmpty() || ciudad.getNombre() == null) {
            throw new WebException("El nombre de la ciudad no puede ser nulo");
        }
        return ciudadRepositorio.save(ciudad);
    }
    
    /*public Ciudad saveByPerson(Ciudad ciudad) throws WebException {
        if (ciudad.getId() == null) {
            throw new WebException("Ocurrio un error al guardar la ciudad");
        }else{
            Optional<Ciudad> optional1 = ciudadRepositorio.findById(ciudad.getId());
            if (optional1.isPresent()) {
                ciudad = optional1.get();
            }
        }
        return ciudadRepositorio.save(ciudad);
    }*/

    public List<Ciudad> listAll() {
        return ciudadRepositorio.findAll();
    }

    public List<Ciudad> listAll(String q) {
        return ciudadRepositorio.findAll("%" + q + "%");
    }

    public Optional<Ciudad> findById(String id) {
        return ciudadRepositorio.findById(id);
    }

    public Ciudad findById(Ciudad ciudad) {
        Optional<Ciudad> optional1 = ciudadRepositorio.findById(ciudad.getId());
            if (optional1.isPresent()) {
                ciudad = optional1.get();
            }
        return ciudad;
    }
    
    @Transactional
    public void delete(Ciudad ciudad) {
        ciudadRepositorio.delete(ciudad);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Ciudad> optional = ciudadRepositorio.findById(id);
        if (optional.isPresent()) {
            Ciudad ciudad = optional.get();
            personaServicio.deleteFieldCiudad(ciudad);
            ciudadRepositorio.delete(ciudad);
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eggcurso.holamundo.repositoritorios;

import com.eggcurso.holamundo.entidades.Ciudad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepocitorio extends JpaRepository<Ciudad, String>{
    @Query("select p from Persona p where p.nombre LIKE :q")
    List<Ciudad> findAll(@Param("q") String q);
}

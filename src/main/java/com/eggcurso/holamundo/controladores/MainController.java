
package com.eggcurso.holamundo.controladores;

import com.eggcurso.holamundo.entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {
    
    //List<Persona> personas = new ArrayList<>();
    
    @GetMapping("")
    public String index(){
        /*if (personas.isEmpty()) {
        personas.add(new Persona("Rodrigo", "Caro", 28));
        personas.add(new Persona("Cosme", "Fulanito", 48));
        personas.add(new Persona("Juan", "Topo", 68));
           
        }
        
       //model.addAttribute("tittle", "probando thymeleaf"); // si quiere que se muestre el titulo agregar tittle en el html
        model.addAttribute("personas",personas);
        */
        return "index";   
    }
    /*
    
    @GetMapping("/crearpersona")
    public String crearpersona(){
        return "personaform";
    }
    
    @PostMapping("/guardarpersona")
    public String guardarpersona(@RequestParam String nombre, 
                      @RequestParam String apellido, @RequestParam Integer edad){
        
        Persona persona = new Persona(nombre, apellido, edad);
        personas.add(persona);
        return "redirect:/";
    }
*/
}

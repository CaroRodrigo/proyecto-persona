package com.eggcurso.holamundo.controladores;

import com.eggcurso.holamundo.entidades.Persona;
import com.eggcurso.holamundo.excepciones.WebException;
import com.eggcurso.holamundo.servicios.CiudadServicio;
import com.eggcurso.holamundo.servicios.PersonaServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/persona")
public class PersonaControler {

    @Autowired
    private PersonaServicio personaServicio;
    
    @Autowired
    private CiudadServicio ciudadServicio;

    @GetMapping("/list")
    public String listarPersonas(Model model,@RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("personas", personaServicio.listallByQ(q));
        }else{
            model.addAttribute("personas", personaServicio.listall());
        }
        return "persona-list";
    }

    @GetMapping("/form")
    public String crearPersona(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Persona> optional = personaServicio.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("persona",optional.get());
            }else {
            return "redirect:/persona/list";
            } 
        }else{
           model.addAttribute("persona",new Persona()); 
        }
        model.addAttribute("ciudades", ciudadServicio.listAll());
        return "persona-form";
    }

    @PostMapping("/save")
    public String guardarPersona(Model model,RedirectAttributes redirectAttributes,Persona persona) {
        
        try {
            personaServicio.save(persona);
            redirectAttributes.addFlashAttribute("error", "Persona guardada con exito");  
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("persona", persona);
            model.addAttribute("ciudades", ciudadServicio.listAll());
        return "persona-form";
        }
        return "redirect:/persona/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String id) {
        personaServicio.deleteById(id);
        return "redirect:/persona/list";
    }
}

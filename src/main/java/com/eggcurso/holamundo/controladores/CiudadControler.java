package com.eggcurso.holamundo.controladores;

import com.eggcurso.holamundo.entidades.Ciudad;
import com.eggcurso.holamundo.excepciones.WebException;
import com.eggcurso.holamundo.servicios.CiudadServicio;
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
@RequestMapping("/ciudad")
public class CiudadControler {

    @Autowired
    private CiudadServicio ciudadServicio;

    @GetMapping("/list")
    public String listarCiudades(Model model,@RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("ciudades", ciudadServicio.listAll(q));
        }else{
            model.addAttribute("ciudades", ciudadServicio.listAll());
        }
        return "ciudad-list";
    }

    @GetMapping("/form")
    public String crearCiudad(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Ciudad> optional = ciudadServicio.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("ciudad",optional.get());
            }else {
            return "redirect:/ciudad/list";
            } 
        }else{
           model.addAttribute("ciudad",new Ciudad()); 
        }
        return "ciudad-form";
    }

    @PostMapping("/save")
    public String guardarCiudad(RedirectAttributes redirectAttributes,Ciudad ciudad) {
        
        try {
            ciudadServicio.save2(ciudad);
            redirectAttributes.addFlashAttribute("error", "Ciudad guardada con exito");  
        } catch (WebException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());        }
        return "redirect:/ciudad/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String id) {
        ciudadServicio.deleteById(id);
        return "redirect:/ciudad/list";
    }
}
